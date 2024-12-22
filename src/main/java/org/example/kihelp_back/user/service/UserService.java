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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.example.kihelp_back.user.util.ErrorMessage.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final WalletService walletService;

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
        var user = findByTelegramId(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                            String.format(USER_NOT_FOUND_BY_TG_ID, username))
                );

        log.info("Successfully loaded user with Telegram ID: {}", user.getTelegramId());
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getTelegramId()),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    @Transactional
    public User save(User user) {
        var existingUser = findByTelegramId(user.getTelegramId());

        log.info("Attempting to check if user with Telegram ID: {} exists if not then create.", user.getTelegramId());
        if (existingUser.isPresent()) {
            if (existingUser.get().isBanned()) {
                throw new UserIsBannedException(String.format(USER_IS_BANNED, user.getTelegramId()));
            }

            existingUser.get().setUsername(user.getUsername());

            log.info("Successfully saved user with Telegram ID: {}", user.getTelegramId());
            return userRepository.save(existingUser.get());
        } else {
            log.info("Successfully saved user with Telegram ID: {}", user.getTelegramId());
            return userRepository.save(user);
        }
    }

    public Optional<User> findByTelegramId(String telegramId) {
        log.info("Attempting to find user by Telegram ID: {}", telegramId);
        var user = userRepository.findByTelegramId(telegramId);

        if (user.isEmpty()) {
            log.warn("No user found with Telegram ID: {}", telegramId);
        }

        return user;
    }

    public User findById(Long id) {
        log.info("Attempting to find user by ID: {}", id);
        return userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException(String.format(USER_NOT_FOUND, id))
        );
    }

    public User findByJwt() {
        log.info("Getting context from SecurityContextHolder");
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        if (authentication == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        var telegramId = authentication.getName();

        return findByTelegramId(telegramId)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format(USER_NOT_FOUND, telegramId))
                );
    }

    public List<User> getAll() {
        log.info("Fetching all users from the database.");
        return userRepository.findAll();
    }

    public List<User> getByRole(String roleName) {
        log.info("Starting to get users by role: {}", roleName);
        var exist = roleService.existsByName(roleName);

        if(!exist){
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND, roleName));
        }

        log.info("Fetching users by role name from the database.");
        return userRepository.findByRoleName(roleName);
    }

    public void changeBan(String telegramId, boolean value) {
        log.info("Attempting to change ban status user with Telegram ID: {}", telegramId);

        var user = findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_TG_ID, telegramId)));

        if (user.isBanned() == value) {
            log.warn("Ban status is already '{}' for user with Telegram ID: {}", value, telegramId);
            return;
        }

        log.info("Changing ban status {} to {} user with Telegram ID: {}",user.isBanned(), value, user.getTelegramId());
        user.setBanned(value);

        if(user.getRoles().contains("ROLE_DEVELOPER")){
            log.info("Deleting developer user with Telegram ID: {}", user.getTelegramId());
            user.getRoles().remove("ROLE_DEVELOPER");
            walletService.deleteByNotDefaultByUser(user.getId());
        }

        userRepository.save(user);
        log.info("Successfully updated ban status for user with Telegram ID: {}", telegramId);
    }

    public void changeRole(String telegramId, String roleName) {
        var role = roleService.findByName(roleName);
        var user = findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_TG_ID, telegramId)));

        validateRoleUserChange(role);

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);

            log.info("Deleting non-default wallets for user: {}", user.getId());
            walletService.deleteByNotDefaultByUser(user.getId());
        } else {
            user.getRoles().add(role);

            var wallet = Wallet.builder()
                    .balance(0.0)
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


    private void validateRoleUserChange(Role role) {
        log.info("Validating user role by {}", role.getName());
        if ("ROLE_USER".equals(role.getName())) {
            throw new IllegalRoleChangeException(USER_ROLE_CHANGE_NOT_ALLOWED);
        }
    }
}
