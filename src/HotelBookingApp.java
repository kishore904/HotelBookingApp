/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * This program demonstrates how booking requests are
 * collected and ordered using a Queue to ensure fairness.
 *
 * @author Kishore
 * @version 5.0
 * -------------------------------------------------------
 */

import java.util.LinkedList;
import java.util.Queue;

// -------------------------------------------------------
// Reservation Class - Represents a booking request
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

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// -------------------------------------------------------
// Booking Request Queue
// -------------------------------------------------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue");
        System.out.println("----------------------------------");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }

        System.out.println("----------------------------------");
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp{

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 5.0 - Booking Request Queue");
        System.out.println("=====================================");

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue order
        queue.displayQueue();
    }
}