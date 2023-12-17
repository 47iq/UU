package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.request.OrderCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${urls.base}/${urls.orders.base}")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            if (request == null) return ResponseEntity.badRequest().body("empty request");
            return ResponseEntity.ok().body(orderService.createOrder(userId.intValue(), request.getAddrId()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserOrders() {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderService.getUserOrders(Math.toIntExact(userId)));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<?> getOrderById(@PathVariable int order_id) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            return ResponseEntity.ok().body(orderService.getOrderById(order_id));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return ResponseUtils.reportError(null, e);
        }
    }
}
