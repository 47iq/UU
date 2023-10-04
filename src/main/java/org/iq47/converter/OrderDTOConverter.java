package org.iq47.converter;

import org.iq47.model.entity.Cart;
import org.iq47.model.entity.Order;
import org.iq47.model.entity.User;
import org.iq47.network.CartDAO;
import org.iq47.network.OrderDTO;

import java.util.stream.Collectors;

public class OrderDTOConverter {
    public static Order dtoToEntity(OrderDTO shopItemDTO) {
        return null;
    }

    public static OrderDTO entityToDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCreated_at(),
                order.getOrderItems().stream().map(OrderItemDTOConverter::entityToDto).collect(Collectors.toList()),
                UserDTOConverter.entityToDto(order.getUser()),
                order.getOrderStatus()
        );
    }
}
