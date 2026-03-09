/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 4: Guest Room Search (Read-Only Access)
 *
 * Guests can view available rooms without modifying
 * the inventory state.
 *
 * @author Kishore
 * @version 4.0
 * -------------------------------------------------------
 */

import java.util.*;

// -------------------------------------------------------
// Room Domain Model
// -------------------------------------------------------
abstract class Room {

    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }

    public String getType() {
        return type;
    }
}

// -------------------------------------------------------
// Concrete Room Types
// -------------------------------------------------------
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 80.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 140.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}

// -------------------------------------------------------
// Inventory (Centralized State)
// -------------------------------------------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0); // Example unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// -------------------------------------------------------
// Search Service (Read-Only)
// -------------------------------------------------------
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(List<Room> rooms) {

        System.out.println("\nAvailable Rooms");
        System.out.println("----------------------------");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            if (available > 0) { // validation: only show available rooms

                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 4.0 - Room Search");
        System.out.println("=====================================");

        // Room domain objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory system
        RoomInventory inventory = new RoomInventory();

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms);
    }
}