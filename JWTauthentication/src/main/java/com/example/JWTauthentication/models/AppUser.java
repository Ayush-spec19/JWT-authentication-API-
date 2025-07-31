package com.example.JWTauthentication.models;

import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppUser {
    private String id;
    private String name;
    private String email;      // ✅ changed from username to email
    private String password;
}
