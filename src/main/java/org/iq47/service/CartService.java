package org.iq47.service;

import org.iq47.network.CartDAO;
import org.iq47.network.response.ResponseWrapper;

public interface CartService {
    CartDAO getUserCart(int userId);
    CartDAO createCart(int userId);
    boolean addShopItemToCart(int userId, int shopItemId);
    boolean removeShopItemFromCart(int userId, int shopItemId);
}
