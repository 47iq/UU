package org.iq47.model;

import org.iq47.model.entity.OrderAddress;
import org.iq47.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
    OrderAddress getByUser(User user);
}
