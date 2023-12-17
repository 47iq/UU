package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.iq47.model.entity.shop.ShopItem;

import java.util.List;

@Data
@AllArgsConstructor
public class ShopDTO {
    int id;
    String name;
    private List<ShopItem> shopItems;
}
