package com.example.JWTauthentication.controllers;

import com.example.JWTauthentication.models.AppUser;
import com.example.JWTauthentication.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test() {
        this.logger.warn("This is working message");
        return "Testing message";
    }

    // ✅ New endpoint to fetch all users (JWT protected)
    @GetMapping("/users")
    public List<AppUser> getUsers(Principal principal) {
        logger.info("Fetching users for: {}", principal.getName());
        return userService.getStore();
    }

@GetMapping("/current-user")
    public String getCurrentUser(Principal principal) {
        return principal.getName(); // Extracts username (email) from SecurityContext
    }
}
