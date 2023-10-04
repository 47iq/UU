package org.iq47.service;

import org.iq47.network.OrderDTO;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface OrderService {
    ResponseWrapper createOrder(int userId, int addrId);
    List<OrderDTO> getUserOrders(int userId);
    OrderDTO getOrderById(int orderId);
}
