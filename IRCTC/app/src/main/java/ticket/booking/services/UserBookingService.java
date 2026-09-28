package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private List<User> userList;

    private User user;

    private static final String USER_FILE_PATH =
            "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    public UserBookingService(User user)
            throws IOException {

        this.user = user;
        loadUserListFromFile();
    }

    private void loadUserListFromFile()
            throws IOException {

        File usersFile =
                new File(USER_FILE_PATH);

        if (!usersFile.exists()) {

            throw new IOException(
                    "Users database file not found: "
                            + usersFile.getAbsolutePath()
            );
        }

        userList = objectMapper.readValue(
                usersFile,
                new TypeReference<List<User>>() {
                }
        );

        if (userList == null) {
            userList = new ArrayList<>();
        }
    }

    public Boolean loginUser() {

        if (user == null) {
            return false;
        }

        Optional<User> foundUser =
                userList.stream()
                        .filter(existingUser ->
                                existingUser.getName()
                                        .equalsIgnoreCase(
                                                user.getName()
                                        )
                                        &&
                                        UserServiceUtil
                                                .checkPassword(
                                                        user.getPassword(),
                                                        existingUser
                                                                .getHashedPassword()
                                                )
                        )
                        .findFirst();

        if (foundUser.isPresent()) {

            user = foundUser.get();

            return true;
        }

        return false;
    }

    public boolean signUp(User newUser) {

        if (newUser == null) {
            return false;
        }

        boolean exists =
                userList.stream()
                        .anyMatch(existingUser ->
                                existingUser.getName()
                                        .equalsIgnoreCase(
                                                newUser.getName()
                                        )
                        );

        if (exists) {

            return exists;
        }

        try {

            userList.add(newUser);

            saveUserListToFile();

        } catch (IOException e) {

            System.out.println(
                    "Error saving user: "
                            + e.getMessage()
            );

        }
        return exists;
    }

    private void saveUserListToFile()
            throws IOException {

        File usersFile =
                new File(USER_FILE_PATH);

        objectMapper.writeValue(
                usersFile,
                userList
        );
    }

    public void fetchBookings() {

        if (user == null) {

            return;
        }

        user.printTickets();
    }

    public List<Train> getTrains(
            String source,
            String destination) {

        try {

            TrainService trainService =
                    new TrainService();

            return trainService.searchTrains(
                    source,
                    destination
            );

        } catch (IOException e) {

            System.out.println(
                    "Error loading trains: "
                            + e.getMessage()
            );

            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(
            Train train) {

        if (train == null ||
                train.getSeats() == null) {

            return new ArrayList<>();
        }

        return train.getSeats();
    }

    public Boolean bookTrainSeat(
            Train train,
            int row,
            int seat) {

        if (train == null) {
            return false;
        }

        try {

            TrainService trainService =
                    new TrainService();

            List<List<Integer>> seats =
                    train.getSeats();

            if (seats == null) {
                return false;
            }

            if (row < 0 ||
                    row >= seats.size()) {

                return false;
            }

            if (seat < 0 ||
                    seat >= seats.get(row).size()) {

                return false;
            }

            if (seats.get(row).get(seat) != 0) {

                return false;
            }

            seats.get(row).set(seat, 1);

            train.setSeats(seats);

            trainService.updateTrain(train);

            return true;

        } catch (IOException e) {

            return false;
        }
    }

    public Boolean cancelBooking(
            String ticketId) {

        if (user == null) {

            return false;
        }

        if (ticketId == null ||
                ticketId.trim().isEmpty()) {

            System.out.println(
                    "Ticket ID cannot be empty."
            );

            return false;
        }

        List<Ticket> tickets =
                user.getTicketsBooked();

        boolean removed =
                tickets.removeIf(ticket ->
                        ticket.getTicketId()
                                .equals(ticketId)
                );

        if (removed) {

            try {

                saveUserListToFile();

                System.out.println(
                        "Ticket " +
                                ticketId +
                                " cancelled successfully."
                );

                return true;

            } catch (IOException e) {

                return false;
            }
        }

        return false;
    }
}