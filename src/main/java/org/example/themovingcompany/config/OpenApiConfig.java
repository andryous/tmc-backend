package org.example.themovingcompany.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Marks this class as a Spring configuration class.
public class OpenApiConfig {

    @Bean // Declares a Spring Bean that will be managed by the container.
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("The Moving Company API")
                        .version("1.0.0")
                        .description("This API allows sales consultants to place, view, update and delete service orders for The Moving Company.")
                        .contact(new Contact()
                                .name("Claudio Rodriguez")
                                .email("mail@claudioandrei.com")
                                .url("https://github.com/andryous"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
