package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.user.Review;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${urls.base}/${urls.reviews.base}")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, ResponseUtils responseUtils) {
        this.reviewService = reviewService;
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody Review review) {
        try {
            return ResponseEntity.ok().body(reviewService.createReview(review));
        } catch (Exception e) {
            return ResponseUtils.reportError(review, e);
        }
    }

    @PutMapping
    private ResponseEntity<?> update(@RequestBody Review review) {
        try {
            return ResponseEntity.ok().body(reviewService.updateReview(review));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Review with the stated id doesn't exist"));
        } catch (Exception e) {
            return ResponseUtils.reportError(review, e);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            reviewService.deleteReviewById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(reviewService.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Review with the stated id doesn't exist"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getReviewsByItemId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(reviewService.getItemReviews(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Item with the stated id doesn't exist"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }
}
