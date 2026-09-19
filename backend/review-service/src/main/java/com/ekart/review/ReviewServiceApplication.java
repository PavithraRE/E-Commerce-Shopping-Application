package com.ekart.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Review Service
 * This service handles product reviews, ratings, and recommendations
 * Uses MongoDB database for review data storage
 */
@SpringBootApplication
public class ReviewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewServiceApplication.class, args);
        System.out.println("========================================");
        System.out.println("Review Service Started Successfully!");
        System.out.println("Port: 8085");
        System.out.println("Swagger UI: http://localhost:8085/api/reviews/swagger-ui.html");
        System.out.println("========================================");
    }
}
