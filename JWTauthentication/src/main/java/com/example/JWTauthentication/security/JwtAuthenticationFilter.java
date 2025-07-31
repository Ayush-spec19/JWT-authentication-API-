package com.example.JWTauthentication.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService; // ✅ will be CustomUserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", requestHeader);

        String email = null;
        String token = null;

        // ✅ Extract JWT token from Authorization header
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7); // remove "Bearer "
            try {
                email = this.jwtHelper.getEmailFromToken(token); // ✅ Extract email from token
                logger.info("Extracted email from token: {}", email);
            } catch (IllegalArgumentException e) {
                logger.error("Error fetching email from token", e);
            } catch (ExpiredJwtException e) {
                logger.error("JWT token expired", e);
            } catch (MalformedJwtException e) {
                logger.error("JWT token is malformed", e);
            }
        } else {
            logger.warn("Invalid or missing Authorization header");
        }

        // ✅ Validate token and set authentication
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (this.jwtHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("JWT token validated successfully, authentication set.");
            } else {
                logger.warn("JWT token validation failed for email: {}", email);
            }
        }

        filterChain.doFilter(request, response);
    }
}
