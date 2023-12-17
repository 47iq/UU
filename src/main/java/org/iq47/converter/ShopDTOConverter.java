package org.iq47.converter;

import org.iq47.model.entity.order.Order;
import org.iq47.model.entity.shop.Shop;
import org.iq47.network.OrderDTO;
import org.iq47.network.ShopDTO;

import java.util.stream.Collectors;

public class ShopDTOConverter {
    public static Shop dtoToEntity(ShopDTO shopDTO) {
        return null;
    }

    public static ShopDTO entityToDto(Shop shop) {
        return new ShopDTO(
                shop.getId(),
                shop.getName(),
                shop.getShopItems()
        );
    }
}
