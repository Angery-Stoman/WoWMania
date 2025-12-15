package com.wowmania.order;

import org.springframework.stereotype.Service;

@Service
public class NotificationService implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.println(">> OBSERVER [Notif]: Alerting Buyer " + order.getBuyerId() + " -> Status: " + order.getStatus());
    }
}