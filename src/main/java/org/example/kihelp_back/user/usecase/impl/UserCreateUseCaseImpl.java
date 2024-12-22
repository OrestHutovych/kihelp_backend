package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.dto.JwtResponse;
import org.example.kihelp_back.user.exception.UserUnauthorizedException;
import org.example.kihelp_back.user.mapper.UserRequestToUserMapper;
import org.example.kihelp_back.user.dto.UserRequest;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.example.kihelp_back.user.util.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_BAD_CREDENTIALS;

@Component
@Slf4j
public class UserCreateUseCaseImpl implements UserCreateUseCase {
    private final UserService userService;
    private final UserRequestToUserMapper userRequestToUserMapper;
    private final RoleService roleService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public UserCreateUseCaseImpl(UserService userService,
                                 UserRequestToUserMapper userRequestToUserMapper,
                                 RoleService roleService,
                                 JwtTokenUtils jwtTokenUtils,
                                 AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRequestToUserMapper = userRequestToUserMapper;
        this.roleService = roleService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtResponse authUser(UserRequest request) {
        var role = roleService.findByName("ROLE_USER");
        var user = userRequestToUserMapper.map(request, role);

        try {
            userService.save(user);

            log.info("Authenticated user with Telegram ID: {}", user.getTelegramId());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.telegramId(),
                    request.telegramId()
            ));
        } catch (BadCredentialsException e) {
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }

        log.info("Loading user details for Telegram ID: {}", user.getTelegramId());
        UserDetails userDetails = userService.loadUserByUsername(user.getTelegramId());
        String jwtToken = jwtTokenUtils.generateToken(userDetails);

        log.info("Successfully generated jwy token for user with Telegram ID: {}", user.getTelegramId());
        return new JwtResponse(jwtToken);
    }
}
