package org.iq47.service;

import org.iq47.model.entity.user.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review);
    void deleteReviewById(int reviewId);
    Review updateReview(Review review);
    Review getById(int reviewId);
    List<Review> getItemReviews(int itemId);
}
