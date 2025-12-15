package com.wowmania.order;

import java.util.UUID;

public class Order {
    private String id;
    private String listingId;
    private String buyerId;
    private String status;

    public Order(String listingId, String buyerId) {
        this.id = UUID.randomUUID().toString();
        this.listingId = listingId;
        this.buyerId = buyerId;
        this.status = "PENDING";
    }

    public void setStatus(String status) { this.status = status; }
    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getBuyerId() { return buyerId; }
}