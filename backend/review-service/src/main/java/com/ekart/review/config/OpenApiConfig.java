package com.ekart.review.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger Configuration for Review Service
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI reviewMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Kart Review Service API")
                        .description("REST API for E-Kart Review & Recommendation Service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("E-Kart Team")
                                .email("support@ekart.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(new Server().url("http://localhost:" + serverPort + "/api/reviews").description("Development Server")));
    }
}
