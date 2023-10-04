package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDAO {
    private int userId;
    private int item_count;
    private List<ShopItemDTO> shopItems;
}
