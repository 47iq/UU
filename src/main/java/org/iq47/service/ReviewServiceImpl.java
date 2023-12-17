package org.iq47.service;

import org.iq47.model.entity.user.Review;
import org.iq47.repository.OrderItemRepository;
import org.iq47.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;

    private final OrderItemRepository itemRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderItemRepository itemRepository) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReviewById(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review updateReview(Review review) {
        if(!reviewRepository.existsById(review.getReviewId())) {
            throw new IllegalArgumentException();
        }
        return reviewRepository.save(review);
    }

    @Override
    public Review getById(int reviewId) {
        if(!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException();
        }
        return reviewRepository.getById(reviewId);
    }

    @Override
    public List<Review> getItemReviews(int itemId) {
        if(!itemRepository.existsById(itemId)) {
            throw new IllegalArgumentException();
        }
        return itemRepository.getReviewsById(itemId);
    }
}
