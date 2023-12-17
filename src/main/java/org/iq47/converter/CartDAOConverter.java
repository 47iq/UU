package org.iq47.converter;

import org.iq47.model.entity.user.Cart;
import org.iq47.network.CartDAO;

import java.util.stream.Collectors;

public class CartDAOConverter {
    public static CartDAO entityToDto(Cart cart) {
        return new CartDAO(cart.getUser().getUid(), cart.getItemCount(), cart.getShopItem().stream().map(ShopItemDTOConverter::entityToDto).collect(Collectors.toList()));
    }
}
