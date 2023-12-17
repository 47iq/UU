package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.ShopDTO;
import org.iq47.network.ShopItemDTO;
import org.iq47.network.request.ShopCreateRequest;
import org.iq47.network.request.ShopItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${urls.base}/${urls.shops.base}")
@Slf4j
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createShop(@RequestBody ShopCreateRequest request) {
        if (request == null) return ResponseEntity.badRequest().body(new ResponseWrapper("body is empty"));
        ShopDTO shop = shopService.createShop(request);

        if (shop != null) {
            return ResponseEntity.ok(shop);
        } else return ResponseEntity.internalServerError().body(new ResponseWrapper("unable to create shop"));
    }

    @GetMapping("/${urls.shops.shop_items}")
    private ResponseEntity<?> getShopItems() {
        List<ShopItemDTO> items = shopService.getAllShopItems();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/${urls.shops.shop_items}/{id}")
    private ResponseEntity<?> getShopItemsByItemId(@PathVariable int id) {
        List<ShopItemDTO> items = shopService.getShopItemsByItemId(id);
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}/${urls.shops.shop_items}")
    private ResponseEntity<?> getShopItems(@PathVariable int id) {
        ShopItemDTO item = shopService.getShopItemById(id);
        if (item == null) return ResponseEntity.status(404).body("shopitem not found");
        return ResponseEntity.ok().body(item);
    }

    @PostMapping("/shop_items/add")
    private ResponseEntity<?> addShopItem(@RequestBody ShopItemCreateRequest request) {
        if (request == null) return ResponseEntity.badRequest().body(new ResponseWrapper("body is empty"));
        return ResponseEntity.ok().body(shopService.addShopItem(request));
    }

}
