package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.dto.JwtDto;
import org.example.kihelp_back.user.exception.UserUnauthorizedException;
import org.example.kihelp_back.user.mapper.UserAuthDtoToUserMapper;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.example.kihelp_back.user.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_BAD_CREDENTIALS;

@Component
@Slf4j
public class UserCreateUseCaseImpl implements UserCreateUseCase {
    private final UserService userService;
    private final UserAuthDtoToUserMapper userRequestToUserMapper;
    private final RoleService roleService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public UserCreateUseCaseImpl(UserService userService,
                                 UserAuthDtoToUserMapper userRequestToUserMapper,
                                 RoleService roleService,
                                 JwtTokenUtils jwtTokenUtils,
                                 AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.userRequestToUserMapper = userRequestToUserMapper;
        this.roleService = roleService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @Value("${telegram.token}")
    private String botToken;

    @Override
    public JwtDto authUser(Map<String, String> query) {
        Role role = roleService.findByName("ROLE_USER");
        User mappedUser = userRequestToUserMapper.map(query, role);

        try {
            userService.save(mappedUser);

            log.info("Authenticated user with Telegram ID: {}", mappedUser.getTelegramId());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    mappedUser.getTelegramId(),
                    mappedUser.getTelegramId()
            ));
        } catch (BadCredentialsException e) {
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }

        log.info("Loading user details for Telegram ID: {}", mappedUser.getTelegramId());
        UserDetails userDetails = userService.loadUserByUsername(mappedUser.getTelegramId());
        String jwtToken = jwtTokenUtils.generateToken(userDetails);

        log.info("Successfully generated jwy token for user with Telegram ID: {}", mappedUser.getTelegramId());
        return new JwtDto(jwtToken);
    }
}
