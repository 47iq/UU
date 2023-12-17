package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ShopDTOConverter;
import org.iq47.converter.ShopItemDTOConverter;
import org.iq47.network.ShopDTO;
import org.iq47.repository.ItemRepository;
import org.iq47.repository.ShopItemRepository;
import org.iq47.repository.ShopRepository;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.shop.Shop;
import org.iq47.model.entity.shop.ShopItem;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.request.ShopItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopServiceImpl implements ShopService{

    private ShopRepository shopRepository;

    private ShopItemRepository shopItemRepository;

    private ItemRepository itemRepository;

    public ShopServiceImpl(ShopRepository shopRepository, ShopItemRepository shopItemRepository, ItemRepository itemRepository) {
        this.shopRepository = shopRepository;
        this.shopItemRepository = shopItemRepository;
        this.itemRepository = itemRepository;
    }

    public ShopDTO createShop(ShopCreateRequest request) {
        if (!request.getShopName().isEmpty()) {
            return ShopDTOConverter.entityToDto(shopRepository.save(new Shop(request.getShopName())));
        }

        return null;
    }

    public List<ShopItemDTO> getAllShopItems() {
        return shopItemRepository.findAll().stream().map(ShopItemDTOConverter::entityToDto).collect(Collectors.toList());
    }

    public ShopItemDTO getShopItemById(int id) {
        return shopItemRepository.findById(id).map(ShopItemDTOConverter::entityToDto).orElse(null);
    }

    public ShopItemDTO addShopItem(ShopItemCreateRequest request) {
        Item item = itemRepository.getById(request.getItem_id());
        Shop shop = shopRepository.getByName(request.getShopName());

        if (shop == null) {
            createShop(new ShopCreateRequest(request.getShopName()));
            shop = shopRepository.getByName(request.getShopName());
        }

        ShopItem shopItem = new ShopItem();
        shopItem.setShop(shop);
        shopItem.setItem(item);
        shopItem.setPrice(request.getPrice());

        shopItem = shopItemRepository.save(shopItem);

        return ShopItemDTOConverter.entityToDto(shopItem);
    }

    public List<ShopItemDTO> getShopItemsByItemId(int itemId) {
        Item item = itemRepository.getItemById(itemId);
        if (item == null) return new ArrayList<>();

        return shopItemRepository.getShopItemsByItem(item).stream().map(ShopItemDTOConverter::entityToDto).collect(Collectors.toList());
    }

}
