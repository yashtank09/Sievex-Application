package com.sievex.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sievex.auth.service.TokenService;
import com.sievex.auth.service.UserService;
import com.sievex.auth.utils.JWTUtil;
import com.sievex.constants.JwtConstants;
import com.sievex.dto.DataApiResponse;
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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JWTUtil jwtUtil, TokenService tokenService, ObjectMapper objectMapper, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isLoginRequest(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String authHeader = request.getHeader(JwtConstants.AUTH_HEADER);

            if (authHeader == null) {
                sendErrorResponse(response, "No authorization header provided", HttpStatus.UNAUTHORIZED);
                logger.warn("No authorization header provided");
                return;
            }

            if (!authHeader.startsWith("Bearer ")) {
                sendErrorResponse(response, "Invalid authorization header format. Expected 'Bearer <token>'", HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = authHeader.substring(7).trim();


            if (jwtUtil.validateTokenFormat(jwtToken)) {
                sendErrorResponse(response, "Invalid JWT token: Invalid token format", HttpStatus.BAD_REQUEST);
            }

            if (tokenService.isTokenBlacklisted(jwtToken)) {
                sendErrorResponse(response, "This token has been invalidated. Please login again.", HttpStatus.UNAUTHORIZED);
            }

            String userName = jwtUtil.extractUserName(jwtToken);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userName);
                if (jwtUtil.isTokenValid(jwtToken, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    sendErrorResponse(response, "Invalid JWT: No username found in token", HttpStatus.UNAUTHORIZED);
                }
            }
            filterChain.doFilter(request, response);

        } catch (MalformedJwtException ex) {
            throw new BadCredentialsException("Invalid JWT token: Malformed token");
        } catch (ExpiredJwtException ex) {
            throw new CredentialsExpiredException("JWT token has expired. Please log in again.", ex);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Unable to process JWT token: " + e.getMessage());
        } catch (Exception e) {
            throw new AuthenticationServiceException("Authentication failed: " + e.getMessage(), e);
        }
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/login") && request.getMethod().equalsIgnoreCase("POST");
    }

    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws
            IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        DataApiResponse<String> errorResponse = new DataApiResponse<>("error", status.value(), message);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
