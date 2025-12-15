package com.wowmania.user;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> payload) {
        User user = UserFactory.createUser(payload.get("username"), payload.get("role"));
        repository.save(user);
        System.out.println("User Created: " + user.getUsername());
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return repository.findById(id);
    }
}