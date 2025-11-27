package com.wowmania.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final List<OrderObserver> observers;

    @Value("${service.marketplace.url}")
    private String marketplaceUrl;

    public OrderService(OrderRepository repository, RestTemplate restTemplate, List<OrderObserver> observers) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.observers = observers;
    }

    public Order createOrder(String listingId, String buyerId) {
        try {
            restTemplate.getForObject(marketplaceUrl + "/listings/" + listingId, Object.class);
        } catch (Exception e) {
            System.out.println("Warning: Listing Check failed (Service might be down), proceeding anyway for demo.");
        }

        Order order = new Order(listingId, buyerId);
        repository.save(order);
        return order;
    }

    public Order updateStatus(String id, String status) {
        Order order = repository.findById(id);
        if (order != null) {
            order.setStatus(status);
            repository.save(order);

            for (OrderObserver observer : observers) {
                observer.update(order);
            }
        }
        return order;
    }
}