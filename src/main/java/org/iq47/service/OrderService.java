package org.iq47.service;

import org.iq47.network.response.ResponseWrapper;

public interface OrderService {
    ResponseWrapper createOrder(long userId);
}
