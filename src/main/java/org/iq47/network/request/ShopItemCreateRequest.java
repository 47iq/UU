package org.iq47.network.request;

import lombok.Data;

@Data
public class ShopItemCreateRequest {
    private String itemName;
    private int shop_id;
    private int price;

}
