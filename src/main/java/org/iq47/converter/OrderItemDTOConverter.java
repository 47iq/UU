package org.iq47.converter;

import org.iq47.model.entity.Order;
import org.iq47.model.entity.OrderItem;
import org.iq47.network.OrderDTO;
import org.iq47.network.OrderItemDTO;

public class OrderItemDTOConverter {
    public static OrderItem dtoToEntity(OrderItemDTO shopItemDTO) {
        return null;
    }

    public static OrderItemDTO entityToDto(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                ShopItemDTOConverter.entityToDto(orderItem.getShopItem()),
                null//OrderDTOConverter.entityToDto(orderItem.getOrder())
        );
    }
}
