import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Reservation class
class Reservation {
    private String customerName;
    private Room room;
    private String checkInDate;
    private String checkOutDate;

    // Constructor
    public Reservation(String customerName, Room room, String checkInDate, String checkOutDate) {
        this.customerName = customerName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Calculate total cost
    public double calculateTotalCost() {
        // For simplicity, assume the cost is calculated as (price * number of days)
        int numberOfDays = 5; // Replace with actual date calculation logic
        return room.getPrice() * numberOfDays;
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation for " + customerName + " in Room " + room.getRoomNumber() +
               " from " + checkInDate + " to " + checkOutDate;
    }
}

// Room class
class Room {
    private int roomNumber;
    private int capacity;
    private double price;
    private List<Reservation> reservations;

    // Constructor
    public Room(int roomNumber, int capacity, double price) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
        this.reservations = new ArrayList<>();
    }

    // Check availability for specific dates
    public boolean isAvailable(String checkInDate, String checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate().equals(checkInDate) || reservation.getCheckOutDate().equals(checkOutDate)) {
                return false; // Room is already reserved for these dates
            }
        }
        return true;
    }

    // Add a reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Remove a reservation
    public boolean removeReservation(Reservation reservation) {
        return reservations.remove(reservation);
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (Capacity: " + capacity + ", Price: $" + price + ")";
    }
}

// Hotel class
class Hotel {
    private String name;
    private String location;
    private List<Room> rooms;
    private List<Reservation> reservations;

    // Constructor
    public Hotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Add a room
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // List all rooms
    public String listRooms() {
        StringBuilder roomList = new StringBuilder("Rooms in " + name + ":\n");
        for (Room room : rooms) {
            roomList.append(room.toString()).append("\n");
        }
        return roomList.toString();
    }

    // Check room availability for specific dates
    public List<Room> getAvailableRooms(String checkInDate, String checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable(checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Make a reservation
    public Reservation makeReservation(String customerName, int roomNumber, String checkInDate, String checkOutDate) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable(checkInDate, checkOutDate)) {
                Reservation reservation = new Reservation(customerName, room, checkInDate, checkOutDate);
                room.addReservation(reservation);
                reservations.add(reservation);
                return reservation;
            }
        }
        return null; // Room not found or not available
    }

    // Cancel a reservation
    public boolean cancelReservation(String customerName, int roomNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equalsIgnoreCase(customerName) &&
                reservation.getRoom().getRoomNumber() == roomNumber) {
                reservation.getRoom().removeReservation(reservation);
                reservations.remove(reservation);
                return true;
            }
        }
        return false; // Reservation not found
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name + " Hotel - " + location;
    }
}

// HotelManagementSystem class
public class HotelManagementSystem {
    private List<Hotel> hotels;

    // Constructor
    public HotelManagementSystem() {
        this.hotels = new ArrayList<>();
    }

    // Add a hotel
    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    // List all hotels
    public String listHotels() {
        StringBuilder hotelList = new StringBuilder("Hotels:\n");
        for (Hotel hotel : hotels) {
            hotelList.append(hotel.toString()).append("\n");
        }
        return hotelList.toString();
    }

    // Search for available rooms across hotels for specific dates
    public List<Room> searchAvailableRooms(String checkInDate, String checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Hotel hotel : hotels) {
            availableRooms.addAll(hotel.getAvailableRooms(checkInDate, checkOutDate));
        }
        return availableRooms;
    }

    // Main menu
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the Gardens Hotel Management System!");
            System.out.println("1. List Hotels");
            System.out.println("2. Search Available Rooms");
            System.out.println("3. Make a Reservation");
            System.out.println("4. Cancel a Reservation");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println(listHotels());
                    break;
                case 2:
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String checkInDate = scanner.nextLine();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String checkOutDate = scanner.nextLine();
                    List<Room> availableRooms = searchAvailableRooms(checkInDate, checkOutDate);
                    if (availableRooms.isEmpty()) {
                        System.out.println("No rooms available for the selected dates.");
                    } else {
                        System.out.println("Available Rooms:");
                        for (Room room : availableRooms) {
                            System.out.println(room);
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter hotel name: ");
                    String hotelName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String resCheckInDate = scanner.nextLine();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String resCheckOutDate = scanner.nextLine();
                    for (Hotel hotel : hotels) {
                        if (hotel.getName().equalsIgnoreCase(hotelName)) {
                            Reservation reservation = hotel.makeReservation(customerName, roomNumber, resCheckInDate, resCheckOutDate);
                            if (reservation != null) {
                                System.out.println("Reservation successful: " + reservation);
                            } else {
                                System.out.println("Room not available or not found.");
                            }
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter hotel name: ");
                    String cancelHotelName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int cancelRoomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter customer name: ");
                    String cancelCustomerName = scanner.nextLine();
                    for (Hotel hotel : hotels) {
                        if (hotel.getName().equalsIgnoreCase(cancelHotelName)) {
                            boolean isCancelled = hotel.cancelReservation(cancelCustomerName, cancelRoomNumber);
                            if (isCancelled) {
                                System.out.println("Reservation cancelled successfully.");
                            } else {
                                System.out.println("Reservation not found.");
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the Gardens Hotel Management System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        HotelManagementSystem system = new HotelManagementSystem();

        // Add sample hotels and rooms
        Hotel hotel1 = new Hotel("Gardens Hotel", "123 Mountain Road");
        hotel1.addRoom(new Room(101, 2, 100.0));
        hotel1.addRoom(new Room(102, 4, 150.0));

        Hotel hotel2 = new Hotel("Caves Resort", "456 Cave Avenue");
        hotel2.addRoom(new Room(201, 3, 120.0));
        hotel2.addRoom(new Room(202, 5, 200.0));

        system.addHotel(hotel1);
        system.addHotel(hotel2);

        // Run the system
        system.run();
    }
}