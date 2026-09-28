package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Ticket {

    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private String dateOfTravel;
    private Train train;

    // Default constructor required by Jackson
    public Ticket() {
    }

    // Parameterized constructor
    public Ticket(
            String ticketId,
            String userId,
            String source,
            String destination,
            String dateOfTravel,
            Train train) {

        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    // =========================
    // ticketId
    // =========================

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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
    // source
    // =========================

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // =========================
    // destination
    // =========================

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // =========================
    // dateOfTravel
    // =========================

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    // =========================
    // train
    // =========================

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    // =========================
    // Ticket information
    // =========================

    public String getTicketInfo() {

        return String.format(
                "Ticket ID: %s belongs to User %s from %s to %s on %s",
                ticketId,
                userId,
                source,
                destination,
                dateOfTravel
        );
    }

    @Override
    public String toString() {

        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", userId='" + userId + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfTravel='" + dateOfTravel + '\'' +
                ", train=" + train +
                '}';
    }
}