package org.iq47.service;

import org.iq47.network.ShopDTO;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.request.ShopItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.List;

public interface ShopService {
    ShopDTO createShop(ShopCreateRequest request);

    List<ShopItemDTO> getAllShopItems();
    ShopItemDTO getShopItemById(int id);
    ResponseWrapper addShopItem(ShopItemCreateRequest request);
    List<ShopItemDTO> getShopItemsByItemId(int itemId);
}
