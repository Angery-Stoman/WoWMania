package com.wowmania.marketplace;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/listings")
public class MarketplaceController {
    private final ListingRepository repository;

    public MarketplaceController(ListingRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Listing createListing(@RequestBody Map<String, Object> payload) {
        Listing listing = new Listing.ListingBuilder(
                (String) payload.get("title"),
                Double.valueOf(payload.get("price").toString()),
                (String) payload.get("sellerId"))
                .withDescription((String) payload.get("description"))
                .withCategory("Service")
                .build();
        repository.save(listing);
        return listing;
    }

    @GetMapping("/{id}")
    public Listing getListing(String id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Listing> getAll() {
        return repository.findAll();
    }
}