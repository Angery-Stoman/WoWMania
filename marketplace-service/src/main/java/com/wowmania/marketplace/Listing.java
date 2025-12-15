package com.wowmania.marketplace;

import java.util.UUID;

public class Listing {
    private String id;
    private String title;
    private double price;
    private String sellerId;
    private String description;
    private String category;

    private Listing(ListingBuilder builder) {
        this.id = UUID.randomUUID().toString();
        this.title = builder.title;
        this.price = builder.price;
        this.sellerId = builder.sellerId;
        this.description = builder.description;
        this.category = builder.category;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getSellerId() { return sellerId; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }

    public static class ListingBuilder {
        private String title;
        private double price;
        private String sellerId;
        private String description = "";
        private String category = "General";

        public ListingBuilder(String title, double price, String sellerId) {
            this.title = title;
            this.price = price;
            this.sellerId = sellerId;
        }

        public ListingBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        public ListingBuilder withCategory(String cat) {
            this.category = cat;
            return this;
        }

        public Listing build() {
            return new Listing(this);
        }
    }
}