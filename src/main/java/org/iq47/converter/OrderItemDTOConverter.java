package org.iq47.converter;

import org.iq47.model.entity.order.OrderItem;
import org.iq47.network.OrderItemDTO;

public class OrderItemDTOConverter {
    public static OrderItemDTO entityToDto(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                ShopItemDTOConverter.entityToDto(orderItem.getShopItem()),
                null
        );
    }
}
