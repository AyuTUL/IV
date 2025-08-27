/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.dao;

import com.travelagency.model.BookingModel;
import java.util.List;

public interface BookingDAO {
    void bookPackage(String userName, String phone, int packageId);
    void cancelBooking(int bookingId);
    List<BookingModel> getUserBookings(String phone);
    List<BookingModel> getAllBookings();
}
