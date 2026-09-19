package com.ekart.cart.controller;

import com.ekart.cart.dto.WishlistItemRequest;
import com.ekart.cart.dto.WishlistResponse;
import com.ekart.cart.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Wishlist operations
 * Provides endpoints for wishlist management
 */
@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Wishlist management APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get wishlist by user ID", description = "Get wishlist details for a specific user")
    public ResponseEntity<WishlistResponse> getWishlistByUserId(@PathVariable Long userId) {
        WishlistResponse response = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add item to wishlist", description = "Add a product to the user's wishlist")
    public ResponseEntity<WishlistResponse> addItemToWishlist(
            @PathVariable Long userId,
            @Valid @RequestBody WishlistItemRequest request) {
        WishlistResponse response = wishlistService.addItemToWishlist(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    @Operation(summary = "Remove item from wishlist", description = "Remove a product from the user's wishlist")
    public ResponseEntity<WishlistResponse> removeItemFromWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        WishlistResponse response = wishlistService.removeItemFromWishlist(userId, productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Clear wishlist", description = "Clear all items from the user's wishlist")
    public ResponseEntity<WishlistResponse> clearWishlist(@PathVariable Long userId) {
        WishlistResponse response = wishlistService.clearWishlist(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/check/{productId}")
    @Operation(summary = "Check if product is in wishlist", description = "Check if a product exists in user's wishlist")
    public ResponseEntity<Boolean> isInWishlist(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        Boolean response = wishlistService.isInWishlist(userId, productId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/delete")
    @Operation(summary = "Delete wishlist", description = "Delete the entire wishlist for a user")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long userId) {
        wishlistService.deleteWishlist(userId);
        return ResponseEntity.noContent().build();
    }
}
