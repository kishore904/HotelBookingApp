// -------------------------------------------------------
// Abstract Room Class
// -------------------------------------------------------
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : $" + price);
    }
}

// -------------------------------------------------------
// Single Room Class
// -------------------------------------------------------
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 80.0);
    }
}

// -------------------------------------------------------
// Double Room Class
// -------------------------------------------------------
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 140.0);
    }
}

// -------------------------------------------------------
// Suite Room Class
// -------------------------------------------------------
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}

// -------------------------------------------------------
// Application Entry Point
// -------------------------------------------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println(" Version 2.1 - Room Initialization");
        System.out.println("=====================================\n");

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        int singleRoomAvailable = 10;
        int doubleRoomAvailable = 6;
        int suiteRoomAvailable = 3;

        System.out.println("Single Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailable);
        System.out.println("--------------------------------");

        System.out.println("Double Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailable);
        System.out.println("--------------------------------");

        System.out.println("Suite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailable);
        System.out.println("--------------------------------");
    }
}