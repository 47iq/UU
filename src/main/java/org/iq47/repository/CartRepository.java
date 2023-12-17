package org.iq47.repository;

import org.iq47.model.entity.user.Cart;
import org.iq47.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser(User user);
}
