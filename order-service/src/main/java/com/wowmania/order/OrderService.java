package com.wowmania.order;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Value("${service.marketplace.url}")
    private String marketplaceUrl;

    public OrderService(OrderRepository repository, RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order createOrder(String listingId, String buyerId) {
        try {
            restTemplate.getForObject(marketplaceUrl + "/listings/" + listingId, Object.class);
        } catch (Exception e) {
            System.out.println("Warning: Listing Check failed (Service might be down), proceeding anyway for demo.");
        }

        Order order = new Order(listingId, buyerId);
        repository.save(order);
        
        rabbitTemplate.convertAndSend("order-notifications", order);
        System.out.println(">> [OrderService] Published creation event to Queue for Order: " + order.getId());
        
        return order;
    }

    public Order updateStatus(String id, String status) {
        Order order = repository.findById(id).orElse(null);

        if (order != null) {
            order.setStatus(status);
            repository.save(order);

            rabbitTemplate.convertAndSend("order-notifications", order);
            System.out.println(">> [OrderService] Published update event to Queue for Order: " + order.getId());
        }
        return order;
    }
}