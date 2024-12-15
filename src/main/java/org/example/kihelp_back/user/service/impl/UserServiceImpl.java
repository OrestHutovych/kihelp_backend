package org.example.kihelp_back.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.repository.UserRepository;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final WalletService walletService;

    public UserServiceImpl(UserRepository userRepository,
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
                .orElseThrow(() -> {
                    log.warn("User not found by Telegram ID: {}. Thrown UsernameNotFoundException", username);
                    return new UsernameNotFoundException(
                            String.format(USER_NOT_FOUND_BY_TG_ID, username)
                    );
                });

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getTelegramId()),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }

    @Override
    public void save(User user) {
        log.info("Starting the save process for user with Telegram ID: {}", user.getTelegramId());
        var existingUser = findByTelegramId(user.getTelegramId());

        if (existingUser.isPresent()) {
            log.info("User with Telegram ID {} already exists. Checking ban status.", user.getTelegramId());

            if (existingUser.get().isBanned()) {
                log.warn("Attempt to save a banned user with Telegram ID: {}", user.getTelegramId());
                throw new UserIsBannedException(String.format(USER_IS_BANNED, user.getTelegramId()));
            }

            log.info("Updating username for user with Telegram ID: {}", user.getTelegramId());
            existingUser.get().setUsername(user.getUsername());

            log.debug("Saving updated user: {}", existingUser.get());
            userRepository.save(existingUser.get());
            log.info("User with Telegram ID {} successfully updated.", user.getTelegramId());
        } else {
            log.info("No existing user found with Telegram ID: {}. Saving new user.", user.getTelegramId());

            log.debug("Default wallet creating for user with Telegram ID: {}", user.getTelegramId());
            Wallet wallet = walletService.createDefaultWallet(user);
            log.info("Wallet adding to user with Telegram ID: {}", user.getTelegramId());
            user.getWallet().add(wallet);

            userRepository.save(user);
            log.info("User with Telegram ID {} successfully saved.", user.getTelegramId());
        }
    }

    @Override
    public Optional<User> findByTelegramId(String telegramId) {
        log.info("Searching for user with Telegram ID: {}", telegramId);
        var user = userRepository.findByTelegramId(telegramId);
        if (user.isPresent()) {
            log.debug("User with Telegram ID {} found: {}", telegramId, user.get());
        } else {
            log.warn("No user found with Telegram ID: {}", telegramId);
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        log.info("Attempting to find user by ID: {}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with ID {} not found. Thrown UserNotFoundException", id);
            return new UserNotFoundException(String.format(USER_NOT_FOUND, id));
        });
    }

    @Override
    public List<User> getAll() {
        log.info("Fetching all users from the database.");
        var users = userRepository.findAll();
        log.debug("Found {} users in the database.", users.size());
        return users;
    }

    @Override
    public List<User> getByRole(String roleName) {
        log.debug("Attempting to find users by role: {}", roleName);
        var exist = roleService.existsByName(roleName);

        if(!exist){
            log.warn("Role with name {} not found. Thrown RoleNotFoundException", roleName);
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND, roleName));
        }

        log.info("Fetching users by role name from the database.");
        var users = userRepository.findByRoleName(roleName);
        log.debug("Found {} users in the database.", users.size());
        return users;
    }

    @Override
    public void changeBan(Long id, boolean value) {
        log.info("Changing ban status for user with ID: {}. New ban status: {}", id, value);

        var user = findById(id);
        user.setBanned(value);

        log.debug("Saving updated ban status for user: {}", user);
        userRepository.save(user);
        log.info("Successfully updated ban status for user with ID: {}", id);
    }
}
