package com.ekart.cart.controller;

import com.ekart.cart.dto.CartItemRequest;
import com.ekart.cart.dto.CartResponse;
import com.ekart.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Cart operations
 * Provides endpoints for cart management
 */
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart management APIs")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get cart by user ID", description = "Get cart details for a specific user")
    public ResponseEntity<CartResponse> getCartByUserId(@PathVariable Long userId) {
        CartResponse response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add item to cart", description = "Add a product to the user's cart")
    public ResponseEntity<CartResponse> addItemToCart(
            @PathVariable Long userId,
            @Valid @RequestBody CartItemRequest request) {
        CartResponse response = cartService.addItemToCart(userId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/items/{productId}")
    @Operation(summary = "Update cart item quantity", description = "Update the quantity of a cart item")
    public ResponseEntity<CartResponse> updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        CartResponse response = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Remove a product from the user's cart")
    public ResponseEntity<CartResponse> removeItemFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        CartResponse response = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Clear cart", description = "Clear all items from the user's cart")
    public ResponseEntity<CartResponse> clearCart(@PathVariable Long userId) {
        CartResponse response = cartService.clearCart(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/delete")
    @Operation(summary = "Delete cart", description = "Delete the entire cart for a user")
    public ResponseEntity<Void> deleteCart(@PathVariable Long userId) {
        cartService.deleteCart(userId);
        return ResponseEntity.noContent().build();
    }
}
