package com.ayush.workoutlogger.dao;

import com.ayush.workoutlogger.model.Exercise;
import java.util.List;

public interface ExerciseDao {
    Exercise create(Exercise e);
    void update(Exercise e);
    void delete(int id);
    Exercise findById(int id);
    List<Exercise> findAll();
}
