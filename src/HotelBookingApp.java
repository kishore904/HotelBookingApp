/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 9: Error Handling & Validation
 *
 * This program demonstrates how booking inputs are
 * validated and handled using custom exceptions.
 *
 * @author Kishore
 * @version 9.0
 * -------------------------------------------------------
 */

import java.util.*;

// -------------------------------------------------------
// Custom Exception for Invalid Booking
// -------------------------------------------------------
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------------------------------------------
// Reservation Class
// -------------------------------------------------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------------------------------------------
// Booking Validator
// -------------------------------------------------------
class BookingValidator {

    private Map<String, Integer> inventory;

    public BookingValidator() {

        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateBooking(Reservation reservation) throws InvalidBookingException {

        String roomType = reservation.getRoomType();

        // Validate room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        // Check availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for " + roomType);
        }

        // Prevent negative inventory
        if (inventory.get(roomType) < 0) {
            throw new InvalidBookingException("Inventory state invalid for " + roomType);
        }
    }

    public void confirmBooking(Reservation reservation) {

        String roomType = reservation.getRoomType();

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 9.0 - Error Handling");
        System.out.println("======================================");

        BookingValidator validator = new BookingValidator();

        // Example booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room"); // unavailable
        Reservation r3 = new Reservation("Charlie", "Luxury Room"); // invalid type

        processBooking(validator, r1);
        processBooking(validator, r2);
        processBooking(validator, r3);
    }

    // Helper method to process bookings safely
    public static void processBooking(BookingValidator validator, Reservation reservation) {

        try {

            validator.validateBooking(reservation);
            validator.confirmBooking(reservation);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }

        System.out.println("--------------------------------------");
    }
}