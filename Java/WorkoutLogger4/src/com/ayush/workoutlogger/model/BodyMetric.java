package com.ayush.workoutlogger.model;

import java.time.LocalDate;

public class BodyMetric {

    private int id;
    private int userId;
    private LocalDate date;
    private double weightKg;
    private Double bodyFat; // nullable
    private String notes;

    public BodyMetric() {
    }

    public BodyMetric(int id, int userId, LocalDate date, double weightKg, Double bodyFat, String notes) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.weightKg = weightKg;
        this.bodyFat = bodyFat;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public Double getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(Double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Weight: " + getWeightKg() + "kg, Body Fat: " + getBodyFat() + "%";
    }

}
