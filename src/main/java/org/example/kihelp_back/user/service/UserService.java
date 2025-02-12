package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.exception.UserRoleNotValidException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.repository.UserRepository;
import org.example.kihelp_back.wallet.exception.WalletNotFoundException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.kihelp_back.user.util.UserErrorMessage.*;
import static org.example.kihelp_back.wallet.util.WalletErrorMessage.WALLET_NOT_FOUND;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final WalletService walletService;

    private static final byte[] WEB_APP_DATA_BYTES = "WebAppData".getBytes(StandardCharsets.UTF_8);

    @Value("${telegram.token}")
    private String botToken;

    public UserService(UserRepository userRepository,
                       RoleService roleService,
                       WalletService walletService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.walletService = walletService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByTelegramId(username);

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getTelegramId()),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    @Transactional
    public User save(User user) {
        boolean existUser = userRepository.existsByTelegramId(user.getTelegramId());

        if (existUser) {
            return auth(user);
        } else {
            return userRepository.save(user);
        }
    }

    @Transactional
    public User auth(User user) {
        User targetUser = findByTelegramId(user.getTelegramId());

        if (targetUser.isBanned()) {
            throw new UserIsBannedException(String.format(USER_IS_BANNED, user.getTelegramId()));
        }

        targetUser.setUsername(user.getUsername());

        return userRepository.save(targetUser);
    }

    @Transactional
    public void updateCourseNumber(Integer courseNumber) {
        User user = findByJwt();

        if(user.getCourseNumber() != null){
            throw new IllegalArgumentException(USER_ALREADY_HAVE_COURSE_NUMBER);
        }

        if(courseNumber != null){
            user.setCourseNumber(courseNumber);
        }

        userRepository.save(user);
    }

    public boolean validateUser(String initData){
        if (initData.length() > 1) {
            initData = initData.substring(1, initData.length() - 1);
        }

        Map<String, String> paramsMap = parseUrlParams(initData);
        String receivedHash = paramsMap.remove("hash");
        if (receivedHash == null) return false;

        String checkParamList = paramsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("\n"));

        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey = new SecretKeySpec(WEB_APP_DATA_BYTES, "HmacSHA256");
            mac.init(secretKey);
            byte[] secretHash = mac.doFinal(botToken.getBytes(StandardCharsets.UTF_8));

            mac.init(new SecretKeySpec(secretHash, "HmacSHA256"));
            byte[] calculatedHashBytes = mac.doFinal(checkParamList.getBytes(StandardCharsets.UTF_8));

            return Arrays.equals(calculatedHashBytes, hexStringToByteArray(receivedHash));
        } catch (Exception e) {
            return false;
        }
    }


    public Map<String, String> parseUrlParams(String urlParams) {
        return Arrays.stream(urlParams.split("&"))
                .map(pair -> pair.split("=", 2))
                .filter(kv -> kv.length == 2)
                .collect(Collectors.toMap(
                        kv -> decode(kv[0]),
                        kv -> decode(kv[1]),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public User findByTelegramId(String telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND, telegramId))
                );
    }

    public User hasDeveloperOrAdminRole(String telegramId) {
        User targetUser = findByTelegramId(telegramId);

        boolean hasRequiredRole = targetUser.getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_DEVELOPER"));

        if (!hasRequiredRole) {
            throw new UserRoleNotValidException(
                    String.format(USER_NOT_VALID_ROLE, targetUser.getTelegramId())
            );
        }

        return targetUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND, id))
                );
    }

    public User findByJwt() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        String telegramId = authentication.getName();

        return findByTelegramId(telegramId);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> getByRole(String roleName) {
        boolean existRole = roleService.existsByName(roleName);

        if(!existRole){
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND, roleName));
        }

        return userRepository.findByRoleName(roleName);
    }

    @Transactional
    public void changeBan(String telegramId, boolean value) {
        User user = findByTelegramId(telegramId);

        user.setBanned(value);

        if(roleService.hasRole("ROLE_DEVELOPER", user) || roleService.hasRole("ROLE_ADMIN", user)){
            List<Role> roles = user.getRoles()
                    .stream()
                    .filter(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_DEVELOPER"))
                    .toList();

            user.getRoles().removeAll(roles);

            Wallet walletForDelete = user.getWallets().stream()
                    .filter(wallet -> !wallet.isDefaultWallet())
                    .findFirst()
                    .orElseThrow(() -> new WalletNotFoundException(WALLET_NOT_FOUND));

            walletService.deleteWallet(walletForDelete);
        }

        userRepository.save(user);
    }

    @Transactional
    public void changeRole(String telegramId, Role role) {
        User targetUser = findByTelegramId(telegramId);

        if(targetUser.getRoles().contains(role)){
            targetUser.getRoles().remove(role);

            if(role.getName().equals("ROLE_DEVELOPER") || role.getName().equals("ROLE_ADMIN")){
                Wallet walletForDelete = targetUser.getWallets().stream()
                                .filter(wallet -> !wallet.isDefaultWallet())
                                .findFirst()
                                .orElseThrow(() -> new WalletNotFoundException(WALLET_NOT_FOUND));

                walletService.deleteWallet(walletForDelete);
            }
        }else{
            targetUser.getRoles().add(role);

            if(role.getName().equals("ROLE_DEVELOPER") || role.getName().equals("ROLE_ADMIN")){
                Wallet wallet = Wallet.builder()
                        .balance(BigDecimal.ZERO)
                        .name("Dev гаманець")
                        .defaultWallet(false)
                        .user(targetUser)
                        .build();
                
                walletService.save(wallet);
            }
        }

        userRepository.save(targetUser);
    }

    public boolean existByTelegramId(String telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    @Scheduled(cron = "0 0 0 1 9 ?")
    public void incrementCourseNumbers(){
        List<User> users = userRepository.findAll();

        for(User user : users){
            Integer courseNumber = user.getCourseNumber();

            if(courseNumber < 4) {
                user.setCourseNumber(courseNumber + 1);
                userRepository.save(user);
            }
        }
    }
}
