package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.request.CartAddRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
@Slf4j
public class CartController {

    private CartService service;

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

    @PostMapping("/cart_create")
    public ResponseEntity<?> createCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.createCart(userId));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getUserCart() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(service.getUserCart(userId));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addShopItemToCart(@RequestBody CartAddRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            if (request == null) return ResponseEntity.badRequest().body("empty request");
            return ResponseEntity.ok().body(service.addShopItemToCart(userId, request.getItem_id()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeShopItemFromCart(@RequestBody CartAddRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            if (request == null) return ResponseEntity.badRequest().body("empty request");
            return ResponseEntity.ok().body(service.removeShopItemFromCart(userId, request.getItem_id()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }


}
