package com.wowmania.user;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "order-notifications")
    public void receiveMessage(OrderMessage message) {
        System.out.println("--------------------------------------------------");
        System.out.println("* [UserService] RECEIVED ASYNC NOTIFICATION:");
        System.out.println("* Emailing User: " + message.getBuyerId());
        System.out.println("* Update: Your order " + message.getId() + " is now " + message.getStatus());
        System.out.println("--------------------------------------------------");
    }
}