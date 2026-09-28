package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utils.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {

        System.out.println(
                "Running Train Booking System"
        );

        Scanner scanner = new Scanner(System.in);

        int option = 0;

        UserBookingService userBookingService;

        try {

            userBookingService =
                    new UserBookingService();

        } catch (IOException e) {

            System.out.println(
                    "Unable to load application data."
            );

            System.out.println(
                    e.getMessage()
            );

            return;
        }

        // Keep selected train between menu operations
        Train trainSelectedForBooking = null;

        while (option != 7) {

            System.out.println();
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");

            option = scanner.nextInt();

            switch (option) {

                // =========================================
                // SIGN UP
                // =========================================

                case 1:

                    System.out.println(
                            "Enter the username to signup"
                    );

                    String nameToSignUp =
                            scanner.next();

                    System.out.println(
                            "Enter the password to signup"
                    );

                    String passwordToSignUp =
                            scanner.next();

                    String hashedPassword =
                            UserServiceUtil.hashPassword(
                                    passwordToSignUp
                            );

                    User userToSignup =
                            new User(
                                    nameToSignUp,
                                    passwordToSignUp,
                                    hashedPassword,
                                    new ArrayList<>(),
                                    UUID.randomUUID().toString()
                            );

                    boolean signupSuccessful =
                            userBookingService
                                    .signUp(userToSignup);

                    if (signupSuccessful) {

                        System.out.println(
                                "Signup successful!"
                        );

                    } else {

                        System.out.println(
                                "Signup failed."
                        );
                    }

                    break;

                // =========================================
                // LOGIN
                // =========================================

                case 2:

                    System.out.println(
                            "Enter the username to Login"
                    );

                    String nameToLogin =
                            scanner.next();

                    System.out.println(
                            "Enter the password to Login"
                    );

                    String passwordToLogin =
                            scanner.next();

                    User userToLogin =
                            new User(
                                    nameToLogin,
                                    passwordToLogin,
                                    "",
                                    new ArrayList<>(),
                                    ""
                            );

                    try {

                        userBookingService =
                                new UserBookingService(
                                        userToLogin
                                );

                        boolean loginSuccessful =
                                userBookingService.loginUser();

                        if (loginSuccessful) {

                            System.out.println(
                                    "Login successful!"
                            );

                        } else {

                            System.out.println(
                                    "Invalid username or password."
                            );
                        }

                    } catch (IOException e) {

                        System.out.println(
                                "Login failed: "
                                        + e.getMessage()
                        );
                    }

                    break;

                // =========================================
                // FETCH BOOKINGS
                // =========================================

                case 3:

                    System.out.println(
                            "Fetching your bookings..."
                    );

                    userBookingService.fetchBookings();

                    break;

                // =========================================
                // SEARCH TRAINS
                // =========================================

                case 4:

                    System.out.println(
                            "Type your source station"
                    );

                    String source =
                            scanner.next();

                    System.out.println(
                            "Type your destination station"
                    );

                    String destination =
                            scanner.next();

                    List<Train> trains =
                            userBookingService.getTrains(
                                    source,
                                    destination
                            );

                    if (trains.isEmpty()) {

                        System.out.println(
                                "No trains found from "
                                        + source
                                        + " to "
                                        + destination
                        );

                        break;
                    }

                    System.out.println();
                    System.out.println(
                            "Available trains:"
                    );

                    int index = 1;

                    for (Train train : trains) {

                        System.out.println(
                                index
                                        + ". Train ID: "
                                        + train.getTrainId()
                                        + " | Train No: "
                                        + train.getTrainNo()
                        );

                        if (train.getStationTimes() != null) {

                            for (
                                    Map.Entry<String, String> entry
                                    : train.getStationTimes()
                                    .entrySet()
                            ) {

                                System.out.println(
                                        "   Station "
                                                + entry.getKey()
                                                + " -> "
                                                + entry.getValue()
                                );
                            }
                        }

                        System.out.println();

                        index++;
                    }

                    System.out.println(
                            "Select a train by typing 1,2,3..."
                    );

                    int selectedTrain =
                            scanner.nextInt();

                    if (
                            selectedTrain < 1
                                    ||
                                    selectedTrain > trains.size()
                    ) {

                        System.out.println(
                                "Invalid train selection."
                        );

                        break;
                    }

                    // Convert 1-based input to 0-based index
                    trainSelectedForBooking =
                            trains.get(
                                    selectedTrain - 1
                            );

                    System.out.println(
                            "Selected train: "
                                    + trainSelectedForBooking
                                    .getTrainInfo()
                    );

                    break;

                // =========================================
                // BOOK SEAT
                // =========================================

                case 5:

                    if (trainSelectedForBooking == null) {

                        System.out.println(
                                "Please search and select a train first."
                        );

                        break;
                    }

                    System.out.println(
                            "Available seats:"
                    );

                    List<List<Integer>> seats =
                            userBookingService
                                    .fetchSeats(
                                            trainSelectedForBooking
                                    );

                    for (
                            int i = 0;
                            i < seats.size();
                            i++
                    ) {

                        System.out.print(
                                "Row " + i + ": "
                        );

                        for (
                                int j = 0;
                                j < seats.get(i).size();
                                j++
                        ) {

                            System.out.print(
                                    seats.get(i).get(j)
                                            + " "
                            );
                        }

                        System.out.println();
                    }

                    System.out.println(
                            "Enter the row"
                    );

                    int row =
                            scanner.nextInt();

                    System.out.println(
                            "Enter the column"
                    );

                    int col =
                            scanner.nextInt();

                    System.out.println(
                            "Booking your seat..."
                    );

                    boolean booked =
                            userBookingService
                                    .bookTrainSeat(
                                            trainSelectedForBooking,
                                            row,
                                            col
                                    );

                    if (booked) {

                        System.out.println(
                                "Booked! Enjoy your journey."
                        );

                    } else {

                        System.out.println(
                                "Can't book this seat."
                        );
                    }

                    break;

                // =========================================
                // CANCEL BOOKING
                // =========================================

                case 6:

                    System.out.println(
                            "Enter the ticket ID to cancel"
                    );

                    String ticketId =
                            scanner.next();

                    userBookingService
                            .cancelBooking(ticketId);

                    break;

                // =========================================
                // EXIT
                // =========================================

                case 7:

                    System.out.println(
                            "Thank you for using Train Booking System."
                    );

                    break;

                // =========================================
                // INVALID OPTION
                // =========================================

                default:

                    System.out.println(
                            "Invalid option. Please select 1-7."
                    );
            }
        }

        scanner.close();
    }
}