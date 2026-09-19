package com.ekart.cart.service;

import com.ekart.cart.dto.CartItemRequest;
import com.ekart.cart.dto.CartResponse;
import com.ekart.cart.model.Cart;
import com.ekart.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for Cart operations
 * Handles cart item management
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    /**
     * Get or create cart for user
     */
    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(userId)));
    }

    /**
     * Get cart by user ID
     */
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return new CartResponse(cart);
    }

    /**
     * Add item to cart
     */
    public CartResponse addItemToCart(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);

        Optional<Cart.CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            Cart.CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setTotalPrice(item.getPrice() * item.getQuantity());
        } else {
            Cart.CartItem newItem = new Cart.CartItem(
                    request.getProductId(),
                    request.getProductName(),
                    request.getProductImage(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getPrice() * request.getQuantity()
            );
            cart.getItems().add(newItem);
        }

        cart = cartRepository.save(cart);
        return new CartResponse(cart);
    }

    /**
     * Update cart item quantity
     */
    public CartResponse updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Cart.CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setTotalPrice(item.getPrice() * quantity);
        }

        cart = cartRepository.save(cart);
        return new CartResponse(cart);
    }

    /**
     * Remove item from cart
     */
    public CartResponse removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        if (!removed) {
            throw new RuntimeException("Item not found in cart");
        }

        cart = cartRepository.save(cart);
        return new CartResponse(cart);
    }

    /**
     * Clear cart
     */
    public CartResponse clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cart = cartRepository.save(cart);
        return new CartResponse(cart);
    }

    /**
     * Delete cart
     */
    public void deleteCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartRepository.delete(cart);
    }
}
