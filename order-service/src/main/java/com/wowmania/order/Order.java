package com.wowmania.order;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // WARNING: 'Order' is a reserved SQL word!

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    private String listingId;
    private String buyerId;
    private String status;

    public Order() { }

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