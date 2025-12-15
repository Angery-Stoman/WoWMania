package com.wowmania.user;

public class OrderMessage {
    private String id;
    private String buyerId;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "OrderMessage{id='" + id + "', buyerId='" + buyerId + "', status='" + status + "'}";
    }
}