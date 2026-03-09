/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 3: Centralized Room Inventory using HashMap
 *
 * This program demonstrates how room availability can be
 * managed using a centralized HashMap instead of scattered
 * variables.
 *
 * @author Kishore
 * @version 3.1
 * -------------------------------------------------------
 */

import java.util.HashMap;
import java.util.Map;

// -------------------------------------------------------
// Inventory Class - Manages room availability
// -------------------------------------------------------
class RoomInventory {

    // Centralized data structure for inventory
    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display inventory
    public void displayInventory() {

        System.out.println("\nCurrent Room Inventory");
        System.out.println("-----------------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("-----------------------------");
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 3.1 - Inventory Setup");
        System.out.println("=======================================");

        // Initialize inventory system
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example inventory update
        System.out.println("\nUpdating availability for Single Room...");
        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();
    }
}