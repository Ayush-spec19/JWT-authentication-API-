package com.example.JWTauthentication.services;

import com.example.JWTauthentication.models.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService {
    private List<AppUser> store = new ArrayList<>();

    public UserService() {
        store.add(new AppUser(UUID.randomUUID().toString(), "Ayush Ojha", "ayush@gmail.com", new BCryptPasswordEncoder().encode("abc")));
        store.add(new AppUser(UUID.randomUUID().toString(), "Rajesh Ojha", "rajesh@gmail.com", new BCryptPasswordEncoder().encode("password2")));
        store.add(new AppUser(UUID.randomUUID().toString(), "Dheeraj Ojha", "dheeraj@gmail.com", new BCryptPasswordEncoder().encode("password3")));
        store.add(new AppUser(UUID.randomUUID().toString(), "Rohan Ojha", "rohan@gmail.com", new BCryptPasswordEncoder().encode("password4")));
    }

    public List<AppUser> getStore() {
        return this.store;
    }
}
