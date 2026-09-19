package com.ekart.review.repository;

import com.ekart.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Review entity
 */
@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByProductId(Long productId);

    Page<Review> findByProductId(Long productId, Pageable pageable);

    List<Review> findByUserId(Long userId);

    List<Review> findByProductIdAndApproved(Long productId, Boolean approved);

    Page<Review> findByProductIdAndApproved(Long productId, Boolean approved, Pageable pageable);

    Review findByProductIdAndUserId(Long productId, Long userId);

    boolean existsByProductIdAndUserId(Long productId, Long userId);
}
