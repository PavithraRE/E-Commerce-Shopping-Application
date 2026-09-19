package com.ekart.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Product Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    private String subcategory;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Stock is required")
    private Integer stock;

    private Double rating = 0.0;

    private Integer reviews = 0;

    @NotBlank(message = "Image is required")
    private String image;

    private List<String> images;

    private Boolean isActive = true;

    private Long sellerId;

    private String sellerName;
}
