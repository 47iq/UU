package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${urls.base}/${urls.carts.base}")
@Slf4j
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/${urls.carts.cart.base}")
    public ResponseEntity<?> createCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.createCart(Math.toIntExact(userId)));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/${urls.carts.cart.base}")
    public ResponseEntity<?> getUserCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.getUserCart(userId.intValue()));
        } catch (ClassCastException e) {
            return ResponseEntity.status(403).body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @PostMapping("/${urls.carts.cart.add}/{itemId}")
    public ResponseEntity<?> addShopItemToCart(@PathVariable int itemId) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

            boolean res = service.addShopItemToCart(userId.intValue(), itemId);

            if (res) return ResponseEntity.ok().build();
            else return ResponseEntity.status(404).body(new ResponseWrapper("shop item not found"));
        } catch (ClassCastException e) {
            return ResponseEntity.status(403).body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @PostMapping("/${urls.carts.cart.remove}/{itemId}")
    public ResponseEntity<?> removeShopItemFromCart(@PathVariable int itemId) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            boolean res = service.removeShopItemFromCart(userId.intValue(), itemId);
            if (res) return ResponseEntity.ok().build();
            else return ResponseEntity.status(404).body(new ResponseWrapper("shop item not found"));
        } catch (ClassCastException e) {
            return ResponseEntity.status(403).body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }


}
