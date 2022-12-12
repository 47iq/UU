package org.iq47.service;

import org.iq47.model.entity.ShopItem;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.request.ShopItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface ShopService {
    ResponseWrapper createShop(ShopCreateRequest request);

    List<ShopItemDTO> getAllShopItems();
    ShopItemDTO getShopItemById(long id);
    ResponseWrapper addShopItem(ShopItemCreateRequest request);
}
