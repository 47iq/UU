package org.iq47.service;

import org.iq47.network.OrderAddressDAO;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface OrderAddressService {
    ResponseWrapper createOrderAddress(int userId, OrderAddressCreateRequest request);
    ResponseWrapper removeOrderAddress(int userId, int addrId);
    List<OrderAddressDAO> getAllOrderAddressesByUser(int userId);
    OrderAddressDAO getAllOrderAddressById(int userId, int addrId);
}
