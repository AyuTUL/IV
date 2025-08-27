/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.model;

public class PackageModel {
    private int id;
    private String name;
    private String duration;
    private double price;

    public PackageModel() {}

    public PackageModel(int id, String name, String duration, double price) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

