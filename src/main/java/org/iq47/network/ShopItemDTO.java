package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopItemDTO {
    private int id;
    private int price;
    private String name;
    private String shopName;

}
