package org.iq47.service;

import org.iq47.model.entity.User;
import org.iq47.network.CartDAO;
import org.iq47.network.response.ResponseWrapper;

public interface CartService {
    CartDAO getUserCart(int userId);
    ResponseWrapper createCart(int userId);
    ResponseWrapper addShopItemToCart(int userId, int shopItemId);
    ResponseWrapper removeShopItemFromCart(int userId, int shopItemId);
}
