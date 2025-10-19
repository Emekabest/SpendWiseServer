package org.example.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class PasswordBcryptService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // strength = log2(rounds). Default is 10, 12 is stronger but slower.
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity (Lambda REST API doesn’t need it)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all requests without auth
                );
        return http.build();
    }



}
