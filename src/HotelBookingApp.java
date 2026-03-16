/**
 * -------------------------------------------------------
 * Book My Stay App
 * Use Case 12: Data Persistence & System Recovery
 *
 * This program demonstrates persistence by saving
 * booking history and inventory to a file and restoring
 * it when the system restarts.
 *
 * @author Kishore
 * @version 12.0
 * -------------------------------------------------------
 */

import java.io.*;
import java.util.*;

// -------------------------------------------------------
// Reservation Class
// -------------------------------------------------------
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------------------------------------------------------
// System State Class
// -------------------------------------------------------
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    public Map<String, Integer> inventory;
    public List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory,
                       List<Reservation> bookingHistory) {

        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// -------------------------------------------------------
// Persistence Service
// -------------------------------------------------------
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state to file
    public void saveState(SystemState state) {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(state);
            out.close();

            System.out.println("System state saved successfully.");

        }
        catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    // Load state from file
    public SystemState loadState() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            SystemState state = (SystemState) in.readObject();
            in.close();

            System.out.println("System state restored successfully.");
            return state;

        }
        catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");

            return new SystemState(new HashMap<>(), new ArrayList<>());
        }
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" BOOK MY STAY APP");
        System.out.println(" Version 12.0 - Persistence & Recovery");
        System.out.println("======================================");

        PersistenceService persistence = new PersistenceService();

        // Load system state
        SystemState state = persistence.loadState();

        // If inventory empty, initialize default inventory
        if (state.inventory.isEmpty()) {

            state.inventory.put("Single Room", 2);
            state.inventory.put("Double Room", 1);
            state.inventory.put("Suite Room", 1);
        }

        // Simulate new booking
        Reservation r1 = new Reservation("RES-301", "Alice", "Single Room");

        state.bookingHistory.add(r1);

        System.out.println("\nBooking History");

        for (Reservation r : state.bookingHistory) {
            r.display();
        }

        System.out.println("\nInventory Snapshot");

        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // Save state before shutdown
        persistence.saveState(state);
    }
}