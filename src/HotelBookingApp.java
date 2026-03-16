/**
 * -------------------------------------------------------
 * Book My Stay App
 * Use Case 11: Concurrent Booking Simulation
 *
 * This program demonstrates how multiple booking
 * requests are processed concurrently while maintaining
 * consistent inventory using synchronization.
 *
 * @author Kishore
 * @version 11.0
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
// Shared Inventory Service
// -------------------------------------------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " allocated " + roomType);

            return true;
        }

        System.out.println(Thread.currentThread().getName()
                + " failed (No rooms available for " + roomType + ")");

        return false;
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------------------------------------------
// Booking Processor (Thread)
// -------------------------------------------------------
class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private InventoryService inventory;

    public BookingProcessor(String name,
                            Queue<Reservation> bookingQueue,
                            InventoryService inventory) {

        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty())
                    break;

                reservation = bookingQueue.poll();
            }

            if (reservation != null) {

                inventory.allocateRoom(reservation.getRoomType());

                try {
                    Thread.sleep(100); // simulate processing delay
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
        System.out.println(" Version 11.0 - Concurrent Booking");
        System.out.println("======================================");

        // Shared booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Single Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Double Room"));
        bookingQueue.add(new Reservation("Emma", "Suite Room"));

        // Shared inventory
        InventoryService inventory = new InventoryService();

        // Multiple booking threads
        BookingProcessor t1 =
                new BookingProcessor("Thread-1", bookingQueue, inventory);

        BookingProcessor t2 =
                new BookingProcessor("Thread-2", bookingQueue, inventory);

        BookingProcessor t3 =
                new BookingProcessor("Thread-3", bookingQueue, inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display final inventory
        inventory.displayInventory();
    }
}