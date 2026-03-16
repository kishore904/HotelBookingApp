/**
 * -------------------------------------------------------
 * Book My Stay App
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * This program demonstrates safe cancellation of bookings
 * by restoring inventory and maintaining consistent system
 * state using Stack for rollback operations.
 *
 * @author Kishore
 * @version 10.0
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
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType +
                " | Room ID: " + roomId);
    }
}

// -------------------------------------------------------
// Booking History
// -------------------------------------------------------
class BookingHistory {

    private Map<String, Reservation> bookings = new HashMap<>();

    public void addBooking(Reservation reservation) {
        bookings.put(reservation.getReservationId(), reservation);
    }

    public Reservation getBooking(String reservationId) {
        return bookings.get(reservationId);
    }

    public void removeBooking(String reservationId) {
        bookings.remove(reservationId);
    }

    public void displayHistory() {

        System.out.println("\nCurrent Booking History");

        for (Reservation r : bookings.values()) {
            r.display();
        }
    }
}

// -------------------------------------------------------
// Inventory Service
// -------------------------------------------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------------------------------------------
// Cancellation Service
// -------------------------------------------------------
class CancellationService {

    private BookingHistory history;
    private InventoryService inventory;

    // Stack for rollback tracking
    private Stack<String> releasedRoomIds = new Stack<>();

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId) {

        Reservation reservation = history.getBooking(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Record released room ID
        releasedRoomIds.push(reservation.getRoomId());

        // Restore inventory
        inventory.incrementRoom(reservation.getRoomType());

        // Remove booking
        history.removeBooking(reservationId);

        System.out.println("Cancellation Successful for Reservation: " + reservationId);
        System.out.println("Released Room ID: " + reservation.getRoomId());
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" BOOK MY STAY APP");
        System.out.println(" Version 10.0 - Booking Cancellation");
        System.out.println("======================================");

        BookingHistory history = new BookingHistory();
        InventoryService inventory = new InventoryService();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES-201", "Alice", "Single Room", "SR-101");
        Reservation r2 = new Reservation("RES-202", "Bob", "Double Room", "DR-201");

        history.addBooking(r1);
        history.addBooking(r2);

        CancellationService cancellationService =
                new CancellationService(history, inventory);

        // Guest cancels booking
        cancellationService.cancelBooking("RES-201");

        // Display updated system state
        history.displayHistory();
        inventory.displayInventory();
    }
}