package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.CartRepository;
import org.iq47.model.OrderAddressRepository;
import org.iq47.model.OrderRepository;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.*;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderAddressRepository orderAddressRepository;

    public ResponseWrapper createOrder(long userId, long addrId) {
        User user = userRepository.getById(userId);
        Cart cart = cartRepository.findCartByUser(user);
        OrderAddress address = orderAddressRepository.getById(addrId);

        Order order = new Order();
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.CREATED);

        for (int i = 0; i < cart.getShopItem().size(); i++) {
            OrderItem orderItem = new OrderItem(cart.getShopItem().get(i), order);
            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);

        return new ResponseWrapper(String.format("created order for %s", userId));
    }
}
