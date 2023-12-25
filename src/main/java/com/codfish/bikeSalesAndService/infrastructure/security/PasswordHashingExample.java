package com.codfish.bikeSalesAndService.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashingExample {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Przykładowe hasło
        String rawPassword = "myPassword123";
        // Haszowanie hasła
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("Encoded password: " + encodedPassword);
    }
}
