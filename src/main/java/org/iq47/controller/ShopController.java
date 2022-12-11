package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.ShopItem;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@Slf4j
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/shop")
    public ResponseEntity<?> createShop(@RequestBody ShopCreateRequest request) {
        ResponseWrapper wrapper = shopService.createShop(request);

        if ( ! wrapper.getMessage().contains("error")) {
            return ResponseEntity.ok(wrapper);
        } else return ResponseEntity.internalServerError().body(wrapper);
    }

    @GetMapping("/shop_items")
    private ResponseEntity<?> getShopItems() {
        List<ShopItemDTO> items = shopService.getAllShopItems();
        return ResponseEntity.ok().body(items);
    }

}
