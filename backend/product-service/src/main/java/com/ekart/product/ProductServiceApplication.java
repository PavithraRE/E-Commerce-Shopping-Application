package com.ekart.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Product Service
 * This service handles product management, categories, and inventory
 * Uses MongoDB database for product data storage
 */
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Product Service Started Successfully!");
        System.out.println("Port: 8082");
        System.out.println("Swagger UI: http://localhost:8082/api/products/swagger-ui.html");
        System.out.println("========================================");
    }
}
