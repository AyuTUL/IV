package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.WorkoutSession;
import java.time.LocalDate;
import java.util.List;

public interface WorkoutSessionDao {
    WorkoutSession create(WorkoutSession s);
    void update(WorkoutSession s);
    void delete(int id);
    WorkoutSession findById(int id);
    List<WorkoutSession> findByUserAndDateRange(int userId, LocalDate from, LocalDate to);
}
