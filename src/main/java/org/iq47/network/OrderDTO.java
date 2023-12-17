package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.iq47.model.entity.order.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private Date created_at;
    private List<OrderItemDTO> orderItems;
    private UserDTO user;
    private OrderStatus orderStatus;
}
