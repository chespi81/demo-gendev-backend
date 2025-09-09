package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI demoGendevOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo GenDev Banking API")
                        .description("REST API for Demo GenDev Banking Application")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ScotiaTech Team")
                                .email("support@scotiatech.com")
                                .url("https://www.scotiatech.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-key", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT token authentication. Enter token without 'Bearer ' prefix.")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
