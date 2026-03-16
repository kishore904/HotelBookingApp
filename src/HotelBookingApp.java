/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 8: Booking History & Reporting
 *
 * This program records confirmed reservations and allows
 * administrators to view booking history and generate
 * simple reports.
 *
 * @author Kishore
 * @version 8.0
 * -------------------------------------------------------
 */

import java.util.*;

// -------------------------------------------------------
// Reservation Class
// -------------------------------------------------------
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------------------------------------------------------
// Booking History
// -------------------------------------------------------
class BookingHistory {

    // List preserves insertion order
    private List<Reservation> bookingList = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        bookingList.add(reservation);
        System.out.println("Reservation stored: " + reservation.getReservationId());
    }

    public List<Reservation> getBookings() {
        return bookingList;
    }
}

// -------------------------------------------------------
// Booking Report Service
// -------------------------------------------------------
class BookingReportService {

    public void generateReport(List<Reservation> bookings) {

        System.out.println("\n===== Booking History Report =====");

        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nTotal Reservations: " + bookings.size());
        System.out.println("==================================");
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 8.0 - Booking History");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();

        // Simulating confirmed reservations
        history.addReservation(new Reservation("RES-101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Charlie", "Suite Room"));

        // Admin generates report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getBookings());
    }
}