package com.ekart.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Cart Service
 * This service handles cart and wishlist operations
 * Uses MongoDB database for cart and wishlist data storage
 */
@SpringBootApplication
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Cart Service Started Successfully!");
        System.out.println("Port: 8084");
        System.out.println("Swagger UI: http://localhost:8084/api/cart/swagger-ui.html");
        System.out.println("========================================");
    }
}
