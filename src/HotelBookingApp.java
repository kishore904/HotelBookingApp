/**
 * -------------------------------------------------------
 * Hotel Booking Management System
 * Use Case 7: Add-On Service Selection
 *
 * This program allows optional services to be attached
 * to a reservation without modifying core booking logic.
 *
 * @author Kishore
 * @version 7.0
 * -------------------------------------------------------
 */

import java.util.*;

// -------------------------------------------------------
// Reservation Class
// -------------------------------------------------------
class Reservation {

    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// -------------------------------------------------------
// Add-On Service Class
// -------------------------------------------------------
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getServiceName() {
        return serviceName;
    }
}

// -------------------------------------------------------
// Add-On Service Manager
// -------------------------------------------------------
class AddOnServiceManager {

    // ReservationID → List of Services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Service added: " + service.getServiceName());
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        System.out.println("\nSelected Services for Reservation: " + reservationId);

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) {
            System.out.println("No services selected.");
            return;
        }

        double totalCost = 0;

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " ($" + s.getPrice() + ")");
            totalCost += s.getPrice();
        }

        System.out.println("Total Add-On Cost: $" + totalCost);
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 7.0 - Add-On Services");
        System.out.println("======================================");

        // Example reservation
        Reservation reservation = new Reservation("RES-101", "Alice");

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation.getReservationId(),
                new AddOnService("Breakfast", 20));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Airport Pickup", 50));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Spa Access", 35));

        // Display services and cost
        manager.displayServices(reservation.getReservationId());
    }
}