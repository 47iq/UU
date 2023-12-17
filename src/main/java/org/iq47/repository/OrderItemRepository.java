package org.iq47.repository;

import org.iq47.model.entity.order.OrderItem;
import org.iq47.model.entity.user.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<Review> getReviewsById(long itemId);
}
