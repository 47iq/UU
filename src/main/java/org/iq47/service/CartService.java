package org.iq47.service;

import org.iq47.model.entity.User;
import org.iq47.network.CartDAO;
import org.iq47.network.response.ResponseWrapper;

public interface CartService {
    CartDAO getUserCart(long userId);
    ResponseWrapper createCart(long userId);
    ResponseWrapper addShopItemToCart(long userId, long shopItemId);
    ResponseWrapper removeShopItemFromCart(long userId, long shopItemId);
}
