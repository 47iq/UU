package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.request.OrderAddressCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${urls.base}/${urls.order_address.base}")
@Slf4j
public class OrderAddressController {

    private final OrderAddressService orderAddressService;

    @Autowired
    public OrderAddressController(OrderAddressService orderAddressService) {
        this.orderAddressService = orderAddressService;
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrderAddress(@RequestBody OrderAddressCreateRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderAddressService.createOrderAddress(userId.intValue(), request));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
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
            return reportError(null, e);
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
            return reportError(null, e);
        }
    }

}
