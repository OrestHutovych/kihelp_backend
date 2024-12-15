package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.dto.JwtResponse;
import org.example.kihelp_back.user.exception.UserUnauthorizedException;
import org.example.kihelp_back.user.mapper.UserRequestToUserMapper;
import org.example.kihelp_back.user.dto.UserRequest;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.impl.UserServiceImpl;
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
    private final UserServiceImpl userService;
    private final UserRequestToUserMapper userRequestToUserMapper;
    private final RoleService roleService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public UserCreateUseCaseImpl(UserServiceImpl userService,
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

        log.debug("Mapping UserRequest {} entity to User objects.", request);
        var user = userRequestToUserMapper.map(request, role);
        log.info("Successfully mapped {} UserRequest to User objects.", user);

        userService.save(user);

        try {
            log.debug("Attempting authentication for Telegram ID: {}", request.telegramId());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.telegramId(),
                    request.telegramId()
            ));
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed for Telegram ID: {}", request.telegramId());
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }

        log.debug("Loading UserDetails for Telegram ID: {}", user.getTelegramId());
        UserDetails userDetails = userService.loadUserByUsername(user.getTelegramId());
        log.debug("Generating JWT token for user: {}", userDetails);
        String jwtToken = jwtTokenUtils.generateToken(userDetails);

        log.info("Successfully generated JWT token for Telegram ID: {}", user.getTelegramId());
        return new JwtResponse(jwtToken);
    }
}
