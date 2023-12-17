package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.OrderDTOConverter;
import org.iq47.model.entity.order.Order;
import org.iq47.model.entity.order.OrderAddress;
import org.iq47.model.entity.order.OrderItem;
import org.iq47.model.entity.order.OrderStatus;
import org.iq47.model.entity.user.Cart;
import org.iq47.model.entity.user.User;
import org.iq47.network.OrderDTO;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private OrderItemRepository orderItemRepository;


    public ResponseWrapper createOrder(int userId, int addrId) {
        User user = userRepository.getById(userId);
        Cart cart = cartRepository.findCartByUser(user);
        OrderAddress address = orderAddressRepository.getById(addrId);

        Order order = new Order();
        order.setAddress(address);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUser(user);
        orderRepository.save(order);

        for (int i = 0; i < cart.getShopItem().size(); i++) {
            OrderItem orderItem = new OrderItem(cart.getShopItem().get(i), order);
            order.getOrderItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }


        cart.setShopItem(new ArrayList<>());
        cartRepository.save(cart);

        return new ResponseWrapper(String.format("created order for %s", userId));
    }

    public List<OrderDTO> getUserOrders(int userId) {
        User user = userRepository.getById(userId);
        return orderRepository.getAllByUser(user).stream().map(OrderDTOConverter::entityToDto).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(int orderId) {
        return OrderDTOConverter.entityToDto(orderRepository.getById(orderId));
    }
}
