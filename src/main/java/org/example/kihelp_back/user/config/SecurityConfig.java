package org.example.kihelp_back.user.config;

import org.example.kihelp_back.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserService userService,
                          JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/v1/users/auth").permitAll()
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{telegramId}/roles").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/role/{roleName}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/users/{telegramId}/ban").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/wallets/deposit").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/transactions/toggle_withdraw_status").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/subjects/subject").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/subjects/{subject_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/subjects/{subject_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/teachers/teacher").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/teachers/{teacher_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/teachers/{teacher_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/tasks/task").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/tasks/{task_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/tasks/{task_id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/invites/invite").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/histories/task/{task_id}/user/{telegram_id}/reseller-check").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/discounts/discount").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/arguments/argument").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/api/v1/arguments/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/arguments/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/arguments/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/actuator/prometheus").permitAll()
                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userService);

        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
