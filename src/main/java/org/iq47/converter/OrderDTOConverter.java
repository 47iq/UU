package org.iq47.converter;

import org.iq47.model.entity.order.Order;
import org.iq47.network.OrderDTO;

import java.util.stream.Collectors;

public class OrderDTOConverter {
    public static OrderDTO entityToDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getOrderItems().stream().map(OrderItemDTOConverter::entityToDto).collect(Collectors.toList()),
                UserDTOConverter.entityToDto(order.getUser()),
                order.getOrderStatus()
        );
    }
}
