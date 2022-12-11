package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDAO {
    private long userId;
    private int item_count;
    private List<ShopItemDTO> shopItems;
}
