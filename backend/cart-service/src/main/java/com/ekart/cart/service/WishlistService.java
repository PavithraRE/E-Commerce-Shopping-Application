package com.ekart.cart.service;

import com.ekart.cart.dto.WishlistItemRequest;
import com.ekart.cart.dto.WishlistResponse;
import com.ekart.cart.model.Wishlist;
import com.ekart.cart.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for Wishlist operations
 * Handles wishlist item management
 */
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    /**
     * Get or create wishlist for user
     */
    private Wishlist getOrCreateWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(userId)));
    }

    /**
     * Get wishlist by user ID
     */
    public WishlistResponse getWishlistByUserId(Long userId) {
        Wishlist wishlist = getOrCreateWishlist(userId);
        return new WishlistResponse(wishlist);
    }

    /**
     * Add item to wishlist
     */
    public WishlistResponse addItemToWishlist(Long userId, WishlistItemRequest request) {
        Wishlist wishlist = getOrCreateWishlist(userId);

        Optional<Wishlist.WishlistItem> existingItem = wishlist.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isEmpty()) {
            Wishlist.WishlistItem newItem = new Wishlist.WishlistItem(
                    request.getProductId(),
                    request.getProductName(),
                    request.getProductImage(),
                    request.getPrice(),
                    request.getRating(),
                    request.getReviews(),
                    request.getCategory()
            );
            wishlist.getItems().add(newItem);
            wishlist = wishlistRepository.save(wishlist);
        }

        return new WishlistResponse(wishlist);
    }

    /**
     * Remove item from wishlist
     */
    public WishlistResponse removeItemFromWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        boolean removed = wishlist.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new RuntimeException("Item not found in wishlist");
        }

        wishlist = wishlistRepository.save(wishlist);
        return new WishlistResponse(wishlist);
    }

    /**
     * Clear wishlist
     */
    public WishlistResponse clearWishlist(Long userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        wishlist.getItems().clear();
        wishlist = wishlistRepository.save(wishlist);
        return new WishlistResponse(wishlist);
    }

    /**
     * Check if product is in wishlist
     */
    public boolean isInWishlist(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId).orElse(null);
        if (wishlist == null) {
            return false;
        }
        return wishlist.getItems().stream().anyMatch(item -> item.getProductId().equals(productId));
    }

    /**
     * Delete wishlist
     */
    public void deleteWishlist(Long userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        wishlistRepository.delete(wishlist);
    }
}
