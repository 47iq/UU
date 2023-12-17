package org.iq47.service;

import org.iq47.network.OrderAddressDAO;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface OrderAddressService {
    OrderAddressDAO createOrderAddress(int userId, OrderAddressCreateRequest request);
    List<OrderAddressDAO> getAllOrderAddressesByUser(int userId);
    OrderAddressDAO getAllOrderAddressById(int userId, int addrId);
}
