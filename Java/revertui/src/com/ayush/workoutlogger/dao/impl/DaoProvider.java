package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.dao.*;
import com.ayush.workoutlogger.dao.db.*;

public class DaoProvider {
    private static final UserDao userDao = new UserDaoDbImpl();
    private static final ExerciseDao exerciseDao = new ExerciseDaoDbImpl();
    private static final WorkoutsDao workoutsDao = new WorkoutsDaoDbImpl();
    private static final BodyMetricDao bodyMetricDao = new BodyMetricDaoDbImpl();
    private static final WorkoutSessionDao sessionDao = new WorkoutSessionDaoDbImpl();

    public static UserDao getUserDao() { return userDao; }
    public static ExerciseDao getExerciseDao() { return exerciseDao; }
    public static WorkoutsDao getWorkoutsDao() { return workoutsDao; }
    public static BodyMetricDao getBodyMetricDao() { return bodyMetricDao; }
    public static WorkoutSessionDao getWorkoutSessionDao() { return sessionDao; }
}
