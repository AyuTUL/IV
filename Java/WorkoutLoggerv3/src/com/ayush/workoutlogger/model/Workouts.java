package com.ayush.workoutlogger.model;

public class Workouts {

    private int id;
    private int sessionId;
    private Exercise exercise;
    private int sets;
    private int reps;
    private double weight;

    public Workouts() {}

    public Workouts(int sessionId, Exercise exercise, int sets, int reps, double weight) {
        this.sessionId = sessionId;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    @Override
    public String toString() {
        return (exercise != null ? exercise.getName() : "Exercise") +
               " - " + sets + "x" + reps + " @ " + weight + "kg";
    }
}
