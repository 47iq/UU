package org.iq47.model;

import org.iq47.model.entity.Order;
import org.iq47.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getAllByUser(User user);
}
