/*
 * 
 * 
 * 
 * 
 * 
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Book class
class Book {
    private String title;
    private boolean isAvailable;

    // Constructor
    public Book(String title) {
        this.title = title;
        this.isAvailable = true; // By default, the book is available
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Check if the book is available
    public boolean isAvailable() {
        return isAvailable;
    }

    // Borrow the book
    public String borrow() {
        if (isAvailable) {
            isAvailable = false;
            return "'" + title + "' has been borrowed.";
        } else {
            return "'" + title + "' is not available.";
        }
    }

    // Return the book
    public String returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            return "'" + title + "' has been returned.";
        } else {
            return "'" + title + "' is already available.";
        }
    }

    // String representation of the book
    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Not Available";
        return "'" + title + "' - " + status;
    }
}

// Library class
class Library {
    // Class attribute for library hours
    public static final String HOURS = "9AM to 5PM daily";

    private String name;
    private String address;
    private List<Book> books;

    // Constructor
    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
    }

    // Add a book to the library
    public String addBook(Book book) {
        books.add(book);
        return "'" + book.getTitle() + "' has been added to " + name + ".";
    }

    // Borrow a book by title
    public String borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book.borrow();
            }
        }
        return "'" + title + "' not found in " + name + ".";
    }

    // Return a book by title
    public String returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book.returnBook();
            }
        }
        return "'" + title + "' not found in " + name + ".";
    }

    // List all books in the library
    public String listBooks() {
        if (books.isEmpty()) {
            return "No books available in " + name + ".";
        }
        StringBuilder bookList = new StringBuilder("Books in " + name + ":\n");
        for (Book book : books) {
            bookList.append(book.toString()).append("\n");
        }
        return bookList.toString();
    }

    // String representation of the library
    @Override
    public String toString() {
        return name + " Library\nAddress: " + address + "\nHours: " + HOURS;
    }
}

// Main class
public class LibrarySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create two libraries
        Library library1 = new Library("Mountain Caves Main Library", "123 Mountain Road");
        Library library2 = new Library("Mountain Caves Branch Library", "456 Cave Avenue");

        // Add books to the libraries
        library1.addBook(new Book("The Great Gatsby"));
        library1.addBook(new Book("1984"));
        library1.addBook(new Book("To Kill a Mockingbird"));

        library2.addBook(new Book("Pride and Prejudice"));
        library2.addBook(new Book("The Catcher in the Rye"));

        // Main menu
        while (true) {
            System.out.println("\nWelcome to the Mountain Caves Library System!");
            System.out.println("1. List Books in Main Library");
            System.out.println("2. List Books in Branch Library");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println(library1.listBooks());
                    break;
                case 2:
                    System.out.println(library2.listBooks());
                    break;
                case 3:
                    System.out.print("Enter the library (1 for Main, 2 for Branch): ");
                    int libraryChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    if (libraryChoice == 1) {
                        System.out.println(library1.borrowBook(borrowTitle));
                    } else if (libraryChoice == 2) {
                        System.out.println(library2.borrowBook(borrowTitle));
                    } else {
                        System.out.println("Invalid library choice.");
                    }
                    break;
                case 4:
                    System.out.print("Enter the library (1 for Main, 2 for Branch): ");
                    int returnLibraryChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter the title of the book to return: ");
                    String returnTitle = scanner.nextLine();
                    if (returnLibraryChoice == 1) {
                        System.out.println(library1.returnBook(returnTitle));
                    } else if (returnLibraryChoice == 2) {
                        System.out.println(library2.returnBook(returnTitle));
                    } else {
                        System.out.println("Invalid library choice.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the Mountain Caves Library System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}