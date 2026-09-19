package com.ekart.cart.dto;

import com.ekart.cart.model.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Wishlist Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {

    private String id;
    private Long userId;
    private List<Wishlist.WishlistItem> items;
    private Integer totalItems;

    public WishlistResponse(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.userId = wishlist.getUserId();
        this.items = wishlist.getItems();
        this.totalItems = wishlist.getItems().size();
    }
}
