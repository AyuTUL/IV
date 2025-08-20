/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.travelagency.service;

import com.travelagency.dao.BookingDAO;
import com.travelagency.dao.BookingDAOImpl;
import com.travelagency.model.BookingModel;

import java.util.List;

public class BookingService {
    private final BookingDAO dao = new BookingDAOImpl();

    public void bookPackage(String userName, String phone, int packageId) {
        dao.bookPackage(userName, phone, packageId);
    }

    public void cancelBooking(int bookingId) {
        dao.cancelBooking(bookingId);
    }

    public List<BookingModel> getUserBookings(String phone) {
        return dao.getUserBookings(phone);
    }

    public List<BookingModel> getAllBookings() {
        return dao.getAllBookings();
    }
}
