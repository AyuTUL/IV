package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.User;
import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    User create(User user);
    List<User> findAll();
}
