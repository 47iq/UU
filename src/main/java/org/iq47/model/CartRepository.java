package org.iq47.model;

import org.iq47.model.entity.Cart;
import org.iq47.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser(User user);
}
