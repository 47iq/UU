package org.iq47.converter;

import org.iq47.model.entity.shop.ShopItem;
import org.iq47.network.ShopItemDTO;

public class ShopItemDTOConverter {
    public static ShopItemDTO entityToDto(ShopItem item) {
        return new ShopItemDTO(item.getId(), item.getPrice(), item.getItem().getName(), item.getShop().getName());
    }
}
