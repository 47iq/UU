package org.iq47.repository;

import org.iq47.model.entity.order.OrderAddress;
import org.iq47.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer> {
    OrderAddress getByUser(User user);
}
