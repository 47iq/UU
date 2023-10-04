package org.iq47.converter;

import org.iq47.model.entity.Cart;
import org.iq47.model.entity.ShopItem;
import org.iq47.network.CartDAO;
import org.iq47.network.ShopItemDTO;

import java.util.stream.Collectors;

public class CartDAOConverter {
    public static Cart dtoToEntity(CartDAO shopItemDTO) {
        return null;
    }

    public static CartDAO entityToDto(Cart cart) {
        return new CartDAO(cart.getUser().getUid(), cart.getItem_count(), cart.getShopItem().stream().map(ShopItemDTOConverter::entityToDto).collect(Collectors.toList()));
    }
}
