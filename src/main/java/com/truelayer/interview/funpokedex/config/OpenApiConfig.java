package com.truelayer.interview.funpokedex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI configuration for Swagger documentation
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FunPokeDex API")
                        .version("1.0.0")
                        .description("""
                            A simple Spring Boot 3 + Java 21 microservice that connects to the PokeAPI to retrieve Pokémon information 
                            and uses the FunTranslations API to translate descriptions into Yoda or Shakespeare style.
                            
                            ## Features
                            - Retrieve Pokémon information from PokeAPI
                            - Translate Pokémon descriptions using FunTranslations API
                            - Graceful fallback for failed translations
                            - Input validation and security measures
                            - Lightweight, reproducible, and production-ready container
                            
                            ## Translation Rules
                            - **Cave habitat or Legendary Pokémon**: Yoda translation
                            - **All other Pokémon**: Shakespeare translation
                            - **Fallback**: Original description if translation fails
                            """)
                        .contact(new Contact()
                                .name("Sara Bellatorre")
                                .email("sara.bellatorre@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server"),
                        new Server()
                                .url("https://api.funpokedex.com")
                                .description("Production server (if available)")
                ));
    }
}