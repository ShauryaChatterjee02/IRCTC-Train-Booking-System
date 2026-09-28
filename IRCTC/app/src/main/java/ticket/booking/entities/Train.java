package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Train {

    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;

    // Default constructor required by Jackson
    public Train() {
    }

    public Train(
            String trainId,
            String trainNo,
            List<List<Integer>> seats,
            Map<String, String> stationTimes,
            List<String> stations) {

        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    // =========================
    // trainId
    // =========================

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    // =========================
    // trainNo
    // =========================

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    // =========================
    // seats
    // =========================

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    // =========================
    // stationTimes
    // =========================

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(
            Map<String, String> stationTimes) {

        this.stationTimes = stationTimes;
    }

    // =========================
    // stations
    // =========================

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    // =========================
    // Train information
    // =========================

    public String getTrainInfo() {

        return String.format(
                "Train ID: %s Train No: %s",
                trainId,
                trainNo
        );
    }

    @Override
    public String toString() {

        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", trainNo='" + trainNo + '\'' +
                ", seats=" + seats +
                ", stationTimes=" + stationTimes +
                ", stations=" + stations +
                '}';
    }
}