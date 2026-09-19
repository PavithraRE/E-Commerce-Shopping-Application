package com.ekart.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Authentication Service
 * This service handles user authentication, registration, and JWT token management
 * Uses MySQL database for user data storage
 */
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Authentication Service Started Successfully!");
        System.out.println("Port: 8081");
        System.out.println("Swagger UI: http://localhost:8081/api/auth/swagger-ui.html");
        System.out.println("========================================");
    }
}
