package org.iq47.converter;

import org.iq47.model.entity.ShopItem;
import org.iq47.network.ShopItemDTO;

public class ShopItemDTOConverter {
    public static ShopItem dtoToEntity(ShopItemDTO shopItemDTO) {
        return null;
    }

    public static ShopItemDTO entityToDto(ShopItem item) {
        return new ShopItemDTO(item.getId(), item.getPrice(), item.getItem().getName(), item.getShop().getName());
    }
}
