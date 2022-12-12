package org.iq47.model;

import org.iq47.model.entity.Cart;
<<<<<<< HEAD
=======
import org.iq47.model.entity.ShopItem;
>>>>>>> caeb03c (Add some more cart endpoints & order/orderaddress stubs)
import org.iq47.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);
}
