package com.ekart.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Wishlist Entity for Cart Service
 * Stores wishlist information in MongoDB
 */
@Document(collection = "wishlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(def = "{'userId': 1}", unique = true)
public class Wishlist {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long userId;

    private List<WishlistItem> items;

    public Wishlist(Long userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WishlistItem {
        private Long productId;
        private String productName;
        private String productImage;
        private Double price;
        private Double rating;
        private Integer reviews;
        private String category;
    }
}
