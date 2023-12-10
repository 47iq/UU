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

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    @PostMapping("/${urls.carts.cart.base}")
    public ResponseEntity<?> createCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.createCart(Math.toIntExact(userId)));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @GetMapping("/${urls.carts.cart.base}")
    public ResponseEntity<?> getUserCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.getUserCart(userId.intValue()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @PostMapping("/${urls.carts.cart.add}/{item_id}")
    public ResponseEntity<?> addShopItemToCart(@PathVariable int item_id) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.addShopItemToCart(userId.intValue(), item_id));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @PostMapping("/${urls.carts.cart.remove}/{item_id}")
    public ResponseEntity<?> removeShopItemFromCart(@PathVariable int item_id) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.removeShopItemFromCart(userId.intValue(), item_id));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }


}
