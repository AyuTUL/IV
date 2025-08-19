package com.ayush.workoutlogger.model;

import java.time.LocalDate;

public class WorkoutSession {
    private int id;
    private int userId;
    private LocalDate date;
    private String notes;

    public WorkoutSession() {}
    public WorkoutSession(int id, int userId, LocalDate date, String notes) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.notes = notes;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
