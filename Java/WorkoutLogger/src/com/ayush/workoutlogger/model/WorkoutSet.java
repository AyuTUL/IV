package com.ayush.workoutlogger.model;

public class WorkoutSet {
    private int id;
    private int sessionId;
    private int exerciseId;
    private int reps;
    private double weightKg;
    private Double rpe; // nullable

    public WorkoutSet(){}
    public WorkoutSet(int id, int sessionId, int exerciseId, int reps, double weightKg, Double rpe) {
        this.id = id;
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.reps = reps;
        this.weightKg = weightKg;
        this.rpe = rpe;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    public Double getRpe() { return rpe; }
    public void setRpe(Double rpe) { this.rpe = rpe; }
}
