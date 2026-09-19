package com.ekart.cart.repository;

import com.ekart.cart.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Wishlist entity
 */
@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    Wishlist findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
