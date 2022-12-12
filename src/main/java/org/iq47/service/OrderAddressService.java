package org.iq47.service;

import org.iq47.model.OrderAddressRepository;
import org.iq47.network.OrderAddressDAO;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface OrderAddressService {
    ResponseWrapper createOrderAddress(long userId, OrderAddressCreateRequest request);
    ResponseWrapper removeOrderAddress(long userId, long addrId);
    List<OrderAddressDAO> getAllOrderAddressesByUser(long userId);
    OrderAddressDAO getAllOrderAddressById(long userId, long addrId);
}
