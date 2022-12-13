package org.iq47.network.request;

import lombok.Data;

@Data
public class ShopItemCreateRequest {
    private String shopName;
    private int item_id;
    private int price;

}
