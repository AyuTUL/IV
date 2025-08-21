package org.personal.workoutlogger.dao;

import java.util.List;
import org.personal.workoutlogger.model.Workout;
import org.personal.workoutlogger.model.WorkoutItem;

public interface WorkoutDao {

    int createWorkout(Workout w);

    int createWorkoutWithItems(Workout w, java.util.List<WorkoutItem> items);

    void addWorkoutItem(WorkoutItem item);

    java.util.List<Workout> getWorkoutsByUser(int userId);

    java.util.List<WorkoutItem> getItemsByWorkout(int workoutId);

    void updateWorkout(Workout w, boolean isTrainer);

    void deleteWorkout(int workoutId, int requesterUserId, boolean isTrainer);
}
