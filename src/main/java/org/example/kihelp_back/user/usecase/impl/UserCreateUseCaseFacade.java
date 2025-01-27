package org.example.kihelp_back.user.usecase.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_BAD_CREDENTIALS;

@Component
@Slf4j
public class UserCreateUseCaseFacade implements UserCreateUseCase {
    private final UserService userService;
    private final UserAuthDtoToUserMapper userRequestToUserMapper;
    private final RoleService roleService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public UserCreateUseCaseFacade(UserService userService,
                                   UserAuthDtoToUserMapper userRequestToUserMapper,
                                   RoleService roleService,
                                   JwtTokenUtils jwtTokenUtils,
                                   AuthenticationManager authenticationManager,
                                   ObjectMapper objectMapper
    ) {
        this.userService = userService;
        this.userRequestToUserMapper = userRequestToUserMapper;
        this.roleService = roleService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public JwtDto authUser(String initData) {
        if(!userService.validateUser(initData)) {
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }

        try {
            Map<String, String> query = userService.parseUrlParams(initData);
            String queryUser = query.get("user");

            Map<String, String> userQuery = objectMapper.readValue(queryUser, Map.class);
            Role role = roleService.findByName("ROLE_USER");
            User mappedUser = userRequestToUserMapper.map(userQuery, role);

            userService.save(mappedUser);

            log.info("Authenticated user with Telegram ID: {}", mappedUser.getTelegramId());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    mappedUser.getTelegramId(),
                    mappedUser.getTelegramId()
            ));

            log.info("Loading user details for Telegram ID: {}", mappedUser.getTelegramId());
            UserDetails userDetails = userService.loadUserByUsername(mappedUser.getTelegramId());
            String jwtToken = jwtTokenUtils.generateToken(userDetails);

            log.info("Successfully generated jwy token for user with Telegram ID: {}", mappedUser.getTelegramId());
            return new JwtDto(jwtToken);
        }catch (Exception e) {
            throw new UserUnauthorizedException(USER_BAD_CREDENTIALS);
        }
    }

    @Override
    public User create(Map<String, String> query) {
        Role role = roleService.findByName("ROLE_USER");
        User mappedUser = userRequestToUserMapper.map(query, role);

        return userService.save(mappedUser);
    }
}
