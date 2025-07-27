package org.example.themovingcompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins(
                                "http://localhost:5173",                    // Local dev
                                "https://tmc-dashboard-new.onrender.com"   // Render frontend
                        )
                        .allowedMethods("*")     // Allow all HTTP methods (GET, POST, etc.)
                        .allowedHeaders("*");    // Allow all headers
            }
        };
    }
}
