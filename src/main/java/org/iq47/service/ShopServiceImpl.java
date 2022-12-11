package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ShopItemDTOConverter;
import org.iq47.model.ShopItemRepository;
import org.iq47.model.ShopRepository;
import org.iq47.model.entity.Shop;
import org.iq47.model.entity.ShopItem;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopItemRepository shopItemRepository;
    public ResponseWrapper createShop(ShopCreateRequest request) {
        if (!request.getShopName().isEmpty()) {
            shopRepository.save(new Shop(request.getShopName()));
            return new ResponseWrapper(String.format("shop %s created", request.getShopName()));
        }

        return new ResponseWrapper(String.format("error creating shop %s: %s", request.getShopName(), ""));
    }

    public List<ShopItemDTO> getAllShopItems() {
        return shopItemRepository.findAll().stream().map(ShopItemDTOConverter::entityToDto).collect(Collectors.toList());
    }
}
