package com.ekart.product.dto;

import com.ekart.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Product Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private String id;
    private Long productId;
    private String name;
    private String category;
    private String subcategory;
    private String description;
    private Double price;
    private Integer stock;
    private Double rating;
    private Integer reviews;
    private String image;
    private java.util.List<String> images;
    private Boolean isActive;
    private Long sellerId;
    private String sellerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.productId = product.getProductId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.subcategory = product.getSubcategory();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.rating = product.getRating();
        this.reviews = product.getReviews();
        this.image = product.getImage();
        this.images = product.getImages();
        this.isActive = product.getIsActive();
        this.sellerId = product.getSellerId();
        this.sellerName = product.getSellerName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }
}
