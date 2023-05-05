package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelManagementRepocitory {
    HashMap<String, Hotel> hotelMap;
    HashMap<Integer, User> userMap;
    HashMap<Integer, List<String>> userHotelMap;

    public HotelManagementRepocitory() {
        this.hotelMap = new HashMap<>();
        this.userMap = new HashMap<>();
        this.userHotelMap = new HashMap<>();
    }

    public boolean containsHotel(String name) {
        return hotelMap.containsKey(name);
    }

    public void addHotel(Hotel hotel) {
        hotelMap.put(hotel.getHotelName(), hotel);
    }

    public void addUser(User user) {
        userMap.put(user.getaadharCardNo(), user);
    }

    public boolean checkRooms(int noOfRooms, String hotelName) {
        Hotel h = hotelMap.get(hotelName);
        if (h.getAvailableRooms() >= noOfRooms) {
            return true;
        }
        return false;
    }

    public void updateAvailableRooms(int noOfRooms, String hotelName) {
        Hotel h = hotelMap.get(hotelName);
        h.setAvailableRooms(h.getAvailableRooms() - noOfRooms);
        hotelMap.put(hotelName, h);
    }

    public int FindAmoundPaid(int noOfRooms, String hotelName) {
        Hotel h = hotelMap.get(hotelName);
        int amoundPerNight = h.getPricePerNight();
        return amoundPerNight * noOfRooms;
    }

    public void addUserhotelmap(int bookingAadharCard, String hotelName) {
        if (userHotelMap.containsKey(bookingAadharCard)) {
            userHotelMap.get(bookingAadharCard).add(hotelName);
        } else {
            List<String> tmp = new ArrayList<>();
            tmp.add(hotelName);
            userHotelMap.put(bookingAadharCard, tmp);
        }
    }

    public int getBooking(Integer aadharCard) {
        if (userHotelMap.containsKey(aadharCard)) {
            return userHotelMap.get(aadharCard).size();
        }
        return 0;
    }

    public Integer getmaxFascility() {
        int max =0;
        for(Hotel x:hotelMap.values()){
            if(x.getFacilities().size()>max){
                max =x.getFacilities().size();
            }
        }
        return max;
    }

    public List<String> getFascilitiesWithMaxValue(Integer max) {
        List<String>tmp = new ArrayList<>();
        for(String x:hotelMap.keySet()){
            if(hotelMap.get(x).getFacilities().size()==max){
                tmp.add(x);
            }
        }
        return tmp;
    }

    public Hotel getHotel(String hotelName) {
        return hotelMap.get(hotelName);
    }

    public void updateFascilities(List<Facility> newFacilities, String hotelName) {
        Hotel h = hotelMap.get(hotelName);
        List<Facility> tmp = h.getFacilities();
        for(Facility x:newFacilities){
            tmp.add(x);
        }
        h.setFacilities(tmp);
        hotelMap.put(hotelName,h);
    }
}
