package com.ayush.workoutlogger.model;

import java.time.LocalDate;

public class WorkoutSession {
    private int id;
    private int userId;
    private LocalDate sessionDate;
    private String notes;

    public WorkoutSession() {}
    public WorkoutSession(int id, int userId, LocalDate sessionDate, String notes) {
        this.id = id;
        this.userId = userId;
        this.sessionDate = sessionDate;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
