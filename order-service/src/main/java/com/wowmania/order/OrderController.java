package com.wowmania.order;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Map<String, String> payload) {
        return service.createOrder(payload.get("listingId"), payload.get("buyerId"));
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable String id, @RequestBody Map<String, String> payload) {
        return service.updateStatus(id, payload.get("status"));
    }
}