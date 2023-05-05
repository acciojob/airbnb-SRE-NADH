package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HotelManagementService {
    HotelManagementRepocitory repocitory = new HotelManagementRepocitory();

    public String addHotel(Hotel hotel) {
        if (hotel == null) {
            return "FAILURE";
        }
        if (hotel.getHotelName() == null) {
            return "FAILURE";
        }
        if (repocitory.containsHotel(hotel.getHotelName())) {
            return "FAILURE";
        }
        repocitory.addHotel(hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        repocitory.addUser(user);
        return user.getaadharCardNo();
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        booking.setBookingId(id);
        if (!repocitory.checkRooms(booking.getNoOfRooms(), booking.getHotelName())) {
            return -1;
        }
        repocitory.updateAvailableRooms(booking.getNoOfRooms(), booking.getHotelName());
        repocitory.addUserhotelmap(booking.getBookingAadharCard(), booking.getHotelName());
        int amound = repocitory.FindAmoundPaid(booking.getNoOfRooms(), booking.getHotelName());
        booking.setAmountToBePaid(amound);
        return amound;
    }

    public int getBookings(Integer aadharCard) {
        return repocitory.getBooking(aadharCard);
    }

    public String getHotelWithMostFascilities() {
        Integer max = repocitory.getmaxFascility();
        if (max == 0) return "";
        List<String> tmp = repocitory.getFascilitiesWithMaxValue(max);
        if (tmp.size() == 1) return tmp.get(0);
        Collections.sort(tmp, String.CASE_INSENSITIVE_ORDER);
        return tmp.get(0);
    }

    public Hotel updateFascilities(List<Facility> newFacilities, String hotelName) {
        Hotel h = repocitory.getHotel(hotelName);
        if (h == null) {
            return h;
        }
        List<Facility> tmp = h.getFacilities();
        newFacilities.removeAll(tmp);
        if (newFacilities.size() > 0) {
            repocitory.updateFascilities(newFacilities, hotelName);
        }
        return repocitory.getHotel(hotelName);
    }
}
