package org.personal.workoutlogger.dao;

import org.personal.workoutlogger.model.User;

public interface UserDao {
    User login(String username, String password, String role);
    boolean createUser(String username, String password, String role) throws Exception;
    boolean updateUser(int id, String newUsername, String newPassword, String role) throws Exception;
    boolean deleteUser(int id) throws Exception;
}

