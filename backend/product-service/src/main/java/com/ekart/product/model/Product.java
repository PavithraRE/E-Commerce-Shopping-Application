package com.ekart.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Product Entity for Product Service
 * Stores product information in MongoDB
 */
@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long productId;

    @Indexed
    private String name;

    @Indexed
    private String category;

    @Indexed
    private String subcategory;

    private String description;

    private Double price;

    private Integer stock;

    private Double rating;

    private Integer reviews;

    private String image;

    private List<String> images;

    private Boolean isActive;

    private Long sellerId;

    private String sellerName;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
