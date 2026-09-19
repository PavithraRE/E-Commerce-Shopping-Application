package com.ekart.review.service;

import com.ekart.review.dto.ReviewRequest;
import com.ekart.review.dto.ReviewResponse;
import com.ekart.review.model.Review;
import com.ekart.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Review operations
 * Handles product reviews and ratings
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * Create a new review
     */
    public ReviewResponse createReview(ReviewRequest request) {
        if (reviewRepository.existsByProductIdAndUserId(request.getProductId(), request.getUserId())) {
            throw new RuntimeException("User has already reviewed this product");
        }

        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setUserId(request.getUserId());
        review.setUserName(request.getUserName());
        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());
        review.setVerified(false); // Will be true if user purchased the product
        review.setApproved(true); // Auto-approve for now, can be moderated
        review.setHelpfulCount(0);

        review = reviewRepository.save(review);
        return new ReviewResponse(review);
    }

    /**
     * Get review by ID
     */
    public ReviewResponse getReviewById(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return new ReviewResponse(review);
    }

    /**
     * Get reviews by product ID
     */
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductIdAndApproved(productId, true);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get reviews by product ID with pagination
     */
    public Page<ReviewResponse> getReviewsByProductIdPaginated(Long productId, int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Review> reviews = reviewRepository.findByProductIdAndApproved(productId, true, pageable);
        return reviews.map(ReviewResponse::new);
    }

    /**
     * Get reviews by user ID
     */
    public List<ReviewResponse> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get all reviews (admin)
     */
    public Page<ReviewResponse> getAllReviews(int page, int size) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(ReviewResponse::new);
    }

    /**
     * Update review
     */
    public ReviewResponse updateReview(String id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());

        review = reviewRepository.save(review);
        return new ReviewResponse(review);
    }

    /**
     * Delete review
     */
    public void deleteReview(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }

    /**
     * Approve review (admin)
     */
    public ReviewResponse approveReview(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setApproved(true);
        review = reviewRepository.save(review);
        return new ReviewResponse(review);
    }

    /**
     * Reject review (admin)
     */
    public ReviewResponse rejectReview(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setApproved(false);
        review = reviewRepository.save(review);
        return new ReviewResponse(review);
    }

    /**
     * Mark review as helpful
     */
    public ReviewResponse markHelpful(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setHelpfulCount(review.getHelpfulCount() + 1);
        review = reviewRepository.save(review);
        return new ReviewResponse(review);
    }

    /**
     * Get average rating for product
     */
    public Double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductIdAndApproved(productId, true);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    /**
     * Get review count for product
     */
    public Long getReviewCount(Long productId) {
        List<Review> reviews = reviewRepository.findByProductIdAndApproved(productId, true);
        return (long) reviews.size();
    }

    /**
     * Get rating distribution for product
     */
    public List<Integer> getRatingDistribution(Long productId) {
        List<Review> reviews = reviewRepository.findByProductIdAndApproved(productId, true);
        int[] distribution = new int[5]; // 1 to 5 stars

        for (Review review : reviews) {
            distribution[review.getRating() - 1]++;
        }

        return List.of(distribution[0], distribution[1], distribution[2], distribution[3], distribution[4]);
    }
}
