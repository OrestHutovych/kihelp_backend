package org.example.kihelp_back.user.usecase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.dto.JwtDto;
import org.example.kihelp_back.user.exception.UserUnauthorizedException;
import org.example.kihelp_back.user.mapper.UserMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.example.kihelp_back.user.util.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.example.kihelp_back.user.util.UserErrorMessage.*;

@Component
@Slf4j
public class UserCreateUseCaseFacade implements UserCreateUseCase {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public UserCreateUseCaseFacade(UserService userService,
                                   UserMapper userMapper,
                                   JwtTokenUtils jwtTokenUtils,
                                   AuthenticationManager authenticationManager,
                                   ObjectMapper objectMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public JwtDto authUser(String initData) {
        if(!userService.validateUser(initData)) {
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }

        Map<String, String> telegramUser = parseTelegramUserData(initData);
        User targetUser = userMapper.toEntity(telegramUser);

        try {
            User savedUser = userService.save(targetUser);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    targetUser.getTelegramId(),
                    targetUser.getTelegramId()
            ));

            UserDetails userDetails = userService.loadUserByUsername(savedUser.getTelegramId());
            String jwtToken = jwtTokenUtils.generateToken(userDetails);

            return new JwtDto(jwtToken);
        } catch (AuthenticationException e) {
            throw new UserUnauthorizedException(String.format(USER_AUTHENTICATION_FAILED, e.getMessage()));
        }
    }

    private Map<String, String> parseTelegramUserData(String initData) {
        Map<String, String> params = userService.parseUrlParams(initData);

        if(!params.containsKey("user")) {
            throw new IllegalArgumentException(MISSING_USER_DATA);
        }

        try {
            return objectMapper.readValue(params.get("user"), Map.class);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException(String.format(INVALID_USER_DATA, e.getMessage()));
        }
    }

    @Override
    public User save(Map<String, String> query) {
        User targetUser = userMapper.toEntity(query);

        return userService.save(targetUser);
    }
}
