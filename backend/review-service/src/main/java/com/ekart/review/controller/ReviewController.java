package com.ekart.review.controller;

import com.ekart.review.dto.ReviewRequest;
import com.ekart.review.dto.ReviewResponse;
import com.ekart.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Review operations
 * Provides endpoints for review management
 */
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Review management APIs")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create a new review", description = "Create a new product review")
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID", description = "Get review details by ID")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable String id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get reviews by product ID", description = "Get all reviews for a specific product")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponse> response = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/paginated")
    @Operation(summary = "Get reviews by product ID with pagination", description = "Get reviews for a product with pagination")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByProductIdPaginated(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReviewResponse> response = reviewService.getReviewsByProductIdPaginated(productId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reviews by user ID", description = "Get all reviews by a specific user")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewResponse> response = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all reviews", description = "Get all reviews with pagination (Admin)")
    public ResponseEntity<Page<ReviewResponse>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReviewResponse> response = reviewService.getAllReviews(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review", description = "Update an existing review")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable String id,
            @Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.updateReview(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete review", description = "Delete a review")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approve")
    @Operation(summary = "Approve review", description = "Approve a review (Admin)")
    public ResponseEntity<ReviewResponse> approveReview(@PathVariable String id) {
        ReviewResponse response = reviewService.approveReview(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Reject review", description = "Reject a review (Admin)")
    public ResponseEntity<ReviewResponse> rejectReview(@PathVariable String id) {
        ReviewResponse response = reviewService.rejectReview(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/helpful")
    @Operation(summary = "Mark review as helpful", description = "Increment helpful count for a review")
    public ResponseEntity<ReviewResponse> markHelpful(@PathVariable String id) {
        ReviewResponse response = reviewService.markHelpful(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/average-rating")
    @Operation(summary = "Get average rating", description = "Get average rating for a product")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        Double response = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/count")
    @Operation(summary = "Get review count", description = "Get total review count for a product")
    public ResponseEntity<Long> getReviewCount(@PathVariable Long productId) {
        Long response = reviewService.getReviewCount(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/distribution")
    @Operation(summary = "Get rating distribution", description = "Get rating distribution for a product")
    public ResponseEntity<List<Integer>> getRatingDistribution(@PathVariable Long productId) {
        List<Integer> response = reviewService.getRatingDistribution(productId);
        return ResponseEntity.ok(response);
    }
}
