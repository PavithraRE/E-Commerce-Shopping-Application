package com.ekart.review.dto;

import com.ekart.review.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Review Response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private String id;
    private Long productId;
    private Long userId;
    private String userName;
    private Integer rating;
    private String title;
    private String comment;
    private Boolean verified;
    private Boolean approved;
    private Integer helpfulCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.productId = review.getProductId();
        this.userId = review.getUserId();
        this.userName = review.getUserName();
        this.rating = review.getRating();
        this.title = review.getTitle();
        this.comment = review.getComment();
        this.verified = review.getVerified();
        this.approved = review.getApproved();
        this.helpfulCount = review.getHelpfulCount();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
