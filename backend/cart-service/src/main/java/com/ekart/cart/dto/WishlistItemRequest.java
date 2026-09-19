package com.ekart.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Wishlist Item Request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    private String productName;

    private String productImage;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    private Double rating;

    private Integer reviews;

    private String category;
}
