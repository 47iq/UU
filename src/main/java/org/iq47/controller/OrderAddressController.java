package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.OrderAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${urls.base}/${urls.order_address.base}")
@Slf4j
public class OrderAddressController {

    private final OrderAddressService orderAddressService;

    public OrderAddressController(OrderAddressService orderAddressService) {
        this.orderAddressService = orderAddressService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrderAddress(@RequestBody OrderAddressCreateRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderAddressService.createOrderAddress(userId.intValue(), request));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrderAddressesByUser() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderAddressService.getAllOrderAddressesByUser(userId.intValue()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }
    @GetMapping("/{addrId}")
    public ResponseEntity<?> getAllOrderAddressById(@PathVariable int addrId) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderAddressService.getAllOrderAddressById(userId.intValue(), addrId));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

}
