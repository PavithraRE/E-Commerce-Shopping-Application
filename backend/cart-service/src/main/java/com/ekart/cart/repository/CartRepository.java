package com.ekart.cart.repository;

import com.ekart.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Cart entity
 */
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Cart findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
