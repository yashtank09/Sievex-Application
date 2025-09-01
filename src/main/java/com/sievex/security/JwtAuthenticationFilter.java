package com.sievex.security;

import com.sievex.auth.service.TokenService;
import com.sievex.auth.service.UserService;
import com.sievex.auth.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public JwtAuthenticationFilter(JWTUtil jwtUtil, TokenService tokenService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            // Skip JWT check for login endpoint
            if (isLoginRequest(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader("Authorization");

            // No token — just continue without setting authentication
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = authHeader.substring(7).trim();
            String userName;

            try {
                jwtUtil.validateTokenFormat(jwtToken);

                if (tokenService.isTokenBlacklisted(jwtToken)) {
                    throw new MalformedJwtException("Token has been invalidated");
                }

                userName = jwtUtil.extractUserName(jwtToken);

                // Set authentication if valid
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(userName);
                    if (jwtUtil.isTokenValid(jwtToken, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException ex) {
                throw ex;
            } catch (JwtException e) {
                throw new JwtException("Invalid token: " + e.getMessage());
            }

            filterChain.doFilter(request, response);
        }  catch (Exception ex) {
            logger.warn("Authentication failed: {}", ex.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed due to invalid token");
        }
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/login") && request.getMethod().equalsIgnoreCase("POST");
    }
}
