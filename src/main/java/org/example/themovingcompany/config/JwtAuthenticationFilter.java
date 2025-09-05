// Specifies the package for this class.
package org.example.themovingcompany.config;

// Import necessary classes for handling HTTP requests, security, and dependency injection.
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.themovingcompany.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Marks this class as a Spring component, making it a candidate for auto-detection and dependency injection.
@Component
// Lombok annotation to generate a constructor with all final fields.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injects the JwtService and UserDetailsService dependencies.
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * This method is executed for each incoming HTTP request.
     * It intercepts the request to check for and validate a JWT token.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Extract the "Authorization" header from the request.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if the header is null or doesn't start with "Bearer ". If so, pass the request to the next filter.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the header (it's after "Bearer ").
        jwt = authHeader.substring(7);
        // Extract the user's email (the subject) from the token using JwtService.
        userEmail = jwtService.extractUsername(jwt);

        // Check if the email is not null AND if the user is not already authenticated in the security context.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the database using the email.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Check if the token is valid for the loaded user.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // If the token is valid, create an authentication token.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need credentials
                        userDetails.getAuthorities()
                );
                // Set additional details for the authentication token from the request.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Update the SecurityContextHolder with the new authentication token. The user is now authenticated.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Pass the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}