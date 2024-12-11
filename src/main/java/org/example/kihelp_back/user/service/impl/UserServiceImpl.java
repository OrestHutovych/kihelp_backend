package org.example.kihelp_back.user.service.impl;

import org.example.kihelp_back.user.exception.UserIsBannedException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.repository.UserRepository;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_IS_BANNED;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
       var existingUser = findByTelegramId(user.getTelegramId());

       if (existingUser.isPresent()) {
           if(existingUser.get().isBanned()){
               throw new UserIsBannedException(String.format(
                       USER_IS_BANNED, user.getTelegramId()
               ));
           }

           existingUser.get().setUsername(user.getUsername());

           userRepository.save(existingUser.get());
       }else{
           userRepository.save(user);
       }
    }

    @Override
    public Optional<User> findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }
}
