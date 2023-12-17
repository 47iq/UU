package org.iq47.repository;

import org.iq47.model.entity.order.Order;
import org.iq47.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByUser(User user);
}
