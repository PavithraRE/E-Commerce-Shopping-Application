package com.ekart.cart.dto;

import com.ekart.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Cart Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private String id;
    private Long userId;
    private List<Cart.CartItem> items;
    private Integer totalItems;
    private Double totalAmount;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUserId();
        this.items = cart.getItems();
        this.totalItems = cart.getItems().stream().mapToInt(Cart.CartItem::getQuantity).sum();
        this.totalAmount = cart.getItems().stream().mapToDouble(Cart.CartItem::getTotalPrice).sum();
    }
}
