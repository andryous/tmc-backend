// Specifies the package where this class belongs.
package org.example.themovingcompany.config;

// Import necessary classes for dependency injection, configuration, and security.
import lombok.RequiredArgsConstructor;
import org.example.themovingcompany.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Marks this class as a Spring configuration class.
@Configuration
// Lombok annotation to generate a constructor with required final fields.
@RequiredArgsConstructor
public class ApplicationConfig {

    // Injects the PersonRepository dependency.
    private final PersonRepository personRepository;

    /**
     * Defines a bean for UserDetailsService.
     * This bean tells Spring Security how to load a user by their username (in our case, email).
     * @return An implementation of UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    /**
     * Defines a bean for the AuthenticationProvider.
     * This is the data access object (DAO) that is responsible for fetching user details
     * and encoding passwords.
     * @return An implementation of AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Defines a bean for the AuthenticationManager.
     * This manager is responsible for authenticating a user.
     * @param config The authentication configuration.
     * @return The configured AuthenticationManager.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines a bean for the PasswordEncoder.
     * We use BCrypt, which is a strong hashing algorithm for storing passwords securely.
     * @return An implementation of PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}