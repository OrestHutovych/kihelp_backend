package org.example.kihelp_back.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.exception.IllegalRoleChangeException;
import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.repository.UserRepository;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.example.kihelp_back.user.util.ErrorMessage.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final WalletService walletService;

    private static final String ROLE_NAME = "ROLE_DEVELOPER";

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
        log.info("Attempting to load user by Telegram ID: {}", username);
        User user = findByTelegramId(username);

        log.info("Successfully loaded user with Telegram ID: {}", user.getTelegramId());
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getTelegramId()),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    @Transactional
    public User save(User user) {
        boolean existUser = userRepository.existsByTelegramId(user.getTelegramId());

        log.info("Attempting to check if user with Telegram ID: {} exists if not then create.", user.getTelegramId());
        if (existUser) {
            User foundedUser = findByTelegramId(user.getTelegramId());

            if (foundedUser.isBanned()) {
                throw new UserIsBannedException(String.format(USER_IS_BANNED, user.getTelegramId()));
            }

            foundedUser.setUsername(user.getUsername());

            log.info("Successfully saved user with Telegram ID: {}", user.getTelegramId());
            return userRepository.save(foundedUser);
        } else {
            log.info("Successfully saved user with Telegram ID: {}", user.getTelegramId());
            return userRepository.save(user);
        }
    }

    public boolean validateUser(String initData){
        if (initData.length() > 1) {
            initData = initData.substring(1, initData.length() - 1);
        }

        Map<String, String> paramsMap = parseUrlParams(initData);

        String receivedHash = paramsMap.get("hash");

        if (receivedHash == null) {
            return false;
        }

        paramsMap.remove("hash");

        List<String> sortedKeys = new ArrayList<>(paramsMap.keySet());
        Collections.sort(sortedKeys);

        StringBuilder checkParamList = new StringBuilder();
        for (String key : sortedKeys) {
            checkParamList.append(key).append("=").append(paramsMap.get(key)).append("\n");
        }

        if (!checkParamList.isEmpty()) {
            checkParamList.setLength(checkParamList.length() - 1);
        }

        try {
            SecretKeySpec secretKey = new SecretKeySpec("WebAppData".getBytes(StandardCharsets.UTF_8), "WebAppData");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            byte[] secretHash = mac.doFinal(botToken.getBytes(StandardCharsets.UTF_8));

            SecretKeySpec secretForData = new SecretKeySpec(secretHash, "HmacSHA256");
            mac.init(secretForData);
            byte[] calculatedHashBytes = mac.doFinal(checkParamList.toString().getBytes(StandardCharsets.UTF_8));

            return Arrays.equals(calculatedHashBytes, hexStringToByteArray(receivedHash));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public Map<String, String> parseUrlParams(String urlParams) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = urlParams.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                try {
                    String key = URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    params.put(key, value);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return params;
    }

    public User findByTelegramId(String telegramId) {
        log.info("Attempting to find user by Telegram ID: {}", telegramId);
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_BY_TG_ID, telegramId))
                );
    }

    public User findById(Long id) {
        log.info("Attempting to find user by ID: {}", id);
        return userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException(String.format(USER_NOT_FOUND, id))
        );
    }

    public User findByJwt() {
        log.info("Start finding User from SecurityContext");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        String telegramId = authentication.getName();
        User foundedUser = findByTelegramId(telegramId);

        log.info("Successfully returned User with Telegram ID: {}", foundedUser.getTelegramId());
        return foundedUser;
    }

    public List<User> getAll() {
        log.info("Fetching all users from the database.");
        return userRepository.findAll();
    }

    public List<User> getByRole(String roleName) {
        log.info("Starting to get users by role: {}", roleName);
        boolean existRole = roleService.existsByName(roleName);

        if(!existRole){
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND, roleName));
        }

        log.info("Successfully fetched users by role name from the database.");
        return userRepository.findByRoleName(roleName);
    }

    public void changeBan(String telegramId, boolean value) {
        log.info("Start to toggle role status to '{}' for user with Telegram ID: {}", value, telegramId);

        User user = findByTelegramId(telegramId);

        if (user.isBanned() == value) {
            log.warn("Ban status is already '{}' for user with Telegram ID: {}", value, telegramId);
            return;
        }

        user.setBanned(value);

        if(user.getRoles().contains(ROLE_NAME)){
            user.getRoles().remove(ROLE_NAME);

            log.info("Attempting to delete not default wallets for user with telegram ID: {}", telegramId);
            walletService.deleteNotDefaultWalletsByUser(user.getId());
        }

        userRepository.save(user);
        log.info("Successfully updated ban status for user with Telegram ID: {}", telegramId);
    }

    public void changeRole(String telegramId, String roleName) {
        Role role = roleService.findByName(roleName);
        User user = findByTelegramId(telegramId);

        validateRoleUserChange(role);

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);

            log.info("Deleting non-default wallets for user: {}", user.getId());
            walletService.deleteNotDefaultWalletsByUser(user.getId());
        } else {
            user.getRoles().add(role);

            var wallet = Wallet.builder()
                    .balance(BigDecimal.ZERO)
                    .name("Dev гаманець")
                    .defaultWallet(false)
                    .user(user)
                    .build();

            log.debug("Saving new dev wallet with ID: {} for user with ID: {}", wallet.getId(), user.getId());
            walletService.save(wallet);
        }

        log.info("Successfully saved user role and update wallet by Telegram ID: {}", telegramId);
        userRepository.save(user);
    }

    public boolean existByTelegramId(String telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }

    private void validateRoleUserChange(Role role) {
        log.info("Validating user role by {}", role.getName());
        if ("ROLE_USER".equals(role.getName())) {
            throw new IllegalRoleChangeException(USER_ROLE_CHANGE_NOT_ALLOWED);
        }
    }
}
