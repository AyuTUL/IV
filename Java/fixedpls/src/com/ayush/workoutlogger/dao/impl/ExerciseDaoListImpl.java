package com.ayush.workoutlogger.dao.impl;

import com.ayush.workoutlogger.dao.ExerciseDao;
import com.ayush.workoutlogger.model.Exercise;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExerciseDaoListImpl implements ExerciseDao {
    private final Map<Integer, Exercise> data = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public ExerciseDaoListImpl() {
        create(new Exercise(0, "Squat", "Legs"));
        create(new Exercise(0, "Bench Press", "Chest"));
        create(new Exercise(0, "Deadlift", "Back"));
        create(new Exercise(0, "Overhead Press", "Shoulders"));
        create(new Exercise(0, "Barbell Row", "Back"));
    }

    @Override
    public Exercise create(Exercise e) {
        int id = seq.getAndIncrement();
        e.setId(id);
        data.put(id, e);
        return e;
    }

    @Override
    public void update(Exercise e) {
        data.put(e.getId(), e);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    @Override
    public Exercise findById(int id) {
        return data.get(id);
    }

    @Override
    public List<Exercise> findAll() {
        return new ArrayList<>(data.values());
    }
}
