/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This program processes booking requests from a queue,
 * allocates rooms safely, and prevents double booking
 * using Set and HashMap data structures.
 *
 * @author Kishore
 * @version 6.0
 * -------------------------------------------------------
 */

import java.util.*;

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
// Booking Request Queue (FIFO)
// -------------------------------------------------------
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------------------------------------------
// Inventory Service
// -------------------------------------------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory");
        System.out.println("----------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------------------------------------------
// Booking Service (Allocation Logic)
// -------------------------------------------------------
class BookingService {

    private InventoryService inventory;

    // Set to enforce unique room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → allocated rooms
    private Map<String, Set<String>> allocationMap = new HashMap<>();

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId;

        do {
            roomId = roomType.replace(" ", "").toUpperCase() + "-" + (100 + allocatedRoomIds.size());
        } while (allocatedRoomIds.contains(roomId));

        allocatedRoomIds.add(roomId);

        allocationMap.putIfAbsent(roomType, new HashSet<>());
        allocationMap.get(roomType).add(roomId);

        // Update inventory
        inventory.decrementRoom(roomType);

        System.out.println("Reservation Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("------------------------------");
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 6.0 - Room Allocation");
        System.out.println("======================================");

        BookingQueue queue = new BookingQueue();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(inventory);

        // Process queue FIFO
        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        // Display remaining inventory
        inventory.displayInventory();
    }
}