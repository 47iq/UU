package org.iq47.network.request;

import lombok.Data;

@Data
public class ShopItemCreateRequest {
    private long item_id;
    private long shop_id;
    private int price;

}
