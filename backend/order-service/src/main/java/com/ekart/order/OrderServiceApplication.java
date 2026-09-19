package com.ekart.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Order Service
 * This service handles order processing, status tracking, and returns
 * Uses MongoDB database for order data storage
 */
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Order Service Started Successfully!");
        System.out.println("Port: 8083");
        System.out.println("Swagger UI: http://localhost:8083/api/orders/swagger-ui.html");
        System.out.println("========================================");
    }
}
