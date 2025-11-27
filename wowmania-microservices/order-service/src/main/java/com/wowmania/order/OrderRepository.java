package com.wowmania.order;

import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final Map<String, Order> db = new ConcurrentHashMap<>();

    public void save(Order order) {
        db.put(order.getId(), order);
    }

    public Order findById(String id) {
        return db.get(id);
    }
}