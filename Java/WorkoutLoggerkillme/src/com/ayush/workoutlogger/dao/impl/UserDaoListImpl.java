package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.model.User;
import com.ayush.workoutlogger.dao.UserDao;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoListImpl implements UserDao {
    private final Map<String, User> users = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public UserDaoListImpl() {
        // demo users
        users.put("admin", new User(seq.getAndIncrement(), "admin", "admin123", "admin"));
        users.put("trainer1", new User(seq.getAndIncrement(), "trainer1", "trainer123", "trainer"));
        users.put("trainee1", new User(seq.getAndIncrement(), "trainee1", "trainee123", "trainee"));
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public User create(User user) {
        if (user.getId() == 0) user.setId(seq.getAndIncrement());
        users.put(user.getUsername(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
