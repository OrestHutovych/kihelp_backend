package org.example.kihelp_back.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.repository.UserRepository;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_IS_BANNED;
import static org.example.kihelp_back.user.util.ErrorMessage.USER_NOT_FOUND;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            log.debug("Saving user: {}", user);
            userRepository.save(user);
            log.info("User with Telegram ID {} successfully saved.", user.getTelegramId());
        }
    }

    @Override
    public Optional<User> findByTelegramId(Long telegramId) {
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
            log.warn("User with ID {} not found.", id);
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
    public void changeBan(Long id, boolean value) {
        log.info("Changing ban status for user with ID: {}. New ban status: {}", id, value);

        var user = findById(id);
        user.setBanned(value);

        log.debug("Saving updated ban status for user: {}", user);
        userRepository.save(user);
        log.info("Successfully updated ban status for user with ID: {}", id);
    }
}
