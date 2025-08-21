package org.personal.workoutlogger.dao;

import java.util.List;
import org.personal.workoutlogger.model.Exercise;

public interface ExerciseDao {

    void add(Exercise ex);

    void update(Exercise ex);

    void delete(int id);

    Exercise get(int id);

    java.util.List<Exercise> searchByName(String q);

    java.util.List<Exercise> getAll();
}
