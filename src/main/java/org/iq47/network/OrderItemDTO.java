package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private int id;
    private ShopItemDTO shopItem;
    private OrderDTO order;
}
