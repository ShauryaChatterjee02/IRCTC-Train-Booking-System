package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String name;
    private String password;
    private String hashedPassword;
    private List<Ticket> ticketsBooked;
    private String userId;

    // Default constructor required by Jackson
    public User() {
    }

    // Parameterized constructor
    public User(
            String name,
            String password,
            String hashedPassword,
            List<Ticket> ticketsBooked,
            String userId) {

        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    // =========================
    // name
    // =========================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // =========================
    // password
    // =========================

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // =========================
    // hashedPassword
    // =========================

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // =========================
    // ticketsBooked
    // =========================

    public List<Ticket> getTicketsBooked() {

        if (ticketsBooked == null) {
            ticketsBooked = new ArrayList<>();
        }

        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    // =========================
    // userId
    // =========================

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // =========================
    // Print tickets
    // =========================

    public void printTickets() {

        if (ticketsBooked == null ||
                ticketsBooked.isEmpty()) {

            System.out.println("No bookings found.");
            return;
        }

        for (Ticket ticket : ticketsBooked) {

            System.out.println(
                    ticket.getTicketInfo()
            );
        }
    }
}