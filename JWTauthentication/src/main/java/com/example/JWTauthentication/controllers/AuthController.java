package com.example.JWTauthentication.controllers;

import com.example.JWTauthentication.models.JwtRequest;
import com.example.JWTauthentication.models.JwtResponse;
import com.example.JWTauthentication.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService; // ✅ Uses InMemoryUserDetailsManager

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        // Authenticate using email & password
        this.doAuthenticate(request.getEmail(), request.getPassword());

        // Load user details by email
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // Generate JWT token
        String token = this.helper.generateToken(userDetails);

        // Return response with token & email
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .email(userDetails.getUsername())  // ✅ email returned as username in UserDetails
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        logger.info("Authenticating user with email: {}", email);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            logger.error("Invalid email or password for: {}", email);
            throw new BadCredentialsException("Invalid Email or Password !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler(BadCredentialsException e) {
        return new ResponseEntity<>("Credentials Invalid !!", HttpStatus.UNAUTHORIZED);
    }
}
