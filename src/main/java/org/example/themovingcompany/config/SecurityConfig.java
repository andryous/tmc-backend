// File: src/main/java/org/example/themovingcompany/config/SecurityConfig.java
package org.example.themovingcompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // --- MODO DEMO: PERMITIR TODAS LAS PETICIONES ---
                        // Esta línea desactiva la seguridad para todos los endpoints.
                        // Cualquier petición a cualquier URL será permitida sin autenticación.
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}