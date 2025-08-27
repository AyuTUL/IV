/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.model;

public class BookingModel {
    private int bookingId;
    private int userId;
    private String userName;
    private String phone;
    private int packageId;
    private String packageName;
    private String duration;
    private double price;

    public BookingModel() {}

    public BookingModel(int bookingId, int userId, String userName, String phone,
                        int packageId, String packageName, String duration, double price) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.packageId = packageId;
        this.packageName = packageName;
        this.duration = duration;
        this.price = price;
    }

    // Getters & Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getPackageId() { return packageId; }
    public void setPackageId(int packageId) { this.packageId = packageId; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

