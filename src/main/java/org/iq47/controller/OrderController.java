package org.iq47.controller;

import lombok.extern.slf4j.Slf4j;
import org.iq47.network.request.OrderCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.iq47.security.userDetails.CustomUserDetails;
import org.iq47.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private ResponseEntity<ResponseWrapper> reportError(Object req, Exception e) {
        if(req != null)
            log.error(String.format("Got %s while processing %s", e.getClass(), req));
        else
            log.error(String.format("Got %s while processing request", e.getClass()));
        return ResponseEntity.internalServerError().body(new ResponseWrapper("Something went wrong"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request) {
        try {
            Long userId = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            if (request == null) return ResponseEntity.badRequest().body("empty request");
            return ResponseEntity.ok().body(orderService.createOrder(userId, request.getAddrId()));
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper("Access denied"));
        } catch (Exception e) {
            return reportError(null, e);
        }
    }
}
