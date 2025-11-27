package com.wowmania.marketplace;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ListingRepository {
    private final Map<String, Listing> db = new ConcurrentHashMap<>();

    public void save(Listing listing) {
        db.put(listing.getId(), listing);
    }

    public Listing findById(String id) {
        return db.get(id);
    }

    public List<Listing> findAll() {
        return new ArrayList<>(db.values());
    }
}