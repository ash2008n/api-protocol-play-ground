package com.lcaohoanq.grpcapi.service;

import com.lcaohoanq.grpcapi.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserDataService {
    
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public UserDataService() {
        // Initialize with sample data
        createUser("John Doe", "john@example.com", "Software Engineer");
        createUser("Jane Smith", "jane@example.com", "Product Manager");
        createUser("Bob Wilson", "bob@example.com", "DevOps Engineer");
    }
    
    public User getUserById(Long id) {
        return users.get(id);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public User createUser(String name, String email, String bio) {
        Long id = idGenerator.getAndIncrement();
        User user = new User(id, name, email, bio);
        users.put(id, user);
        return user;
    }
}
