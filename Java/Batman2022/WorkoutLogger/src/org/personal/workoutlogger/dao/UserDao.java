package org.personal.workoutlogger.dao;
import org.personal.workoutlogger.model.User;
public interface UserDao {
  User findByUsername(String username);
  User login(String username,String rawPassword);
  boolean register(String username,String rawPassword,String role);
}
