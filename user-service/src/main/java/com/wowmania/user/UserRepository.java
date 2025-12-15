package com.wowmania.user;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<String, User> db = new ConcurrentHashMap<>();

    public void save(User user) {
        db.put(user.getId(), user);
    }

    public User findById(String id) {
        return db.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }
}