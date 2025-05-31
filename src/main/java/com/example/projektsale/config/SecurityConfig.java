package com.example.projektsale.config;

import org.springframework.http.HttpMethod;
import com.example.projektsale.entity.User;
import com.example.projektsale.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                User appUser = user.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .roles(appUser.getRole().name())
                        .build();
            }
            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()


                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/equipment/computer").hasRole("ADMIN")
                        .requestMatchers("/api/equipment/projector").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/equipment/**").hasRole("ADMIN")


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