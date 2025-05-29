package com.example.projektsale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // ADMIN-ONLY
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/equipment/computer").hasRole("ADMIN")
                        .requestMatchers("/api/equipment/projector").hasRole("ADMIN")

                        // USER and ADMIN
                        .requestMatchers("/api/rooms/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/reservations/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/equipment/**").hasAnyRole("USER", "ADMIN")


                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }
}