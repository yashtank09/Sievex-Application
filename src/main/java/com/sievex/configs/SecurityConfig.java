package com.sievex.configs;

import com.sievex.auth.enums.UserRole;
import com.sievex.auth.service.SecurityConfigService;
import com.sievex.security.JwtAuthenticationEntryPoint;
import com.sievex.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final SecurityConfigService securityConfigService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> {
                    List<String> publicPaths = securityConfigService.getPublicPaths();
                    if (!publicPaths.isEmpty()) {
                        auth.requestMatchers(publicPaths.toArray(new String[0])).permitAll();
                    }

                    List<String> adminPaths = securityConfigService.getAdminPaths();
                    if (!adminPaths.isEmpty()) {
                        auth.requestMatchers(adminPaths.toArray(new String[0])).hasAnyRole(UserRole.ADMIN.getRole());
                    }

                    List<String> moderatorPaths = securityConfigService.getModeratorPaths();
                    if (!moderatorPaths.isEmpty()) {
                        auth.requestMatchers(moderatorPaths.toArray(new String[0])).hasAnyRole(UserRole.ADMIN.getRole(), UserRole.MODERATOR.getRole());
                    }

                    List<String> userPaths = securityConfigService.getUserPaths();
                    if (!userPaths.isEmpty()) {
                        auth.requestMatchers(userPaths.toArray(new String[0])).hasAnyRole(UserRole.USER.getRole(), UserRole.MODERATOR.getRole(), UserRole.ADMIN.getRole());
                    }
                }).exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Custom entry point for handling unauthorized access
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
