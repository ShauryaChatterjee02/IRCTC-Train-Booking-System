package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {

    private final List<Train> trainList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TRAIN_DB_PATH =
            "app/src/main/java/ticket/booking/localDb/trains.json";

    public TrainService() throws IOException {

        File trainsFile = new File(TRAIN_DB_PATH);

        System.out.println(
                "Loading trains from: "
                        + trainsFile.getAbsolutePath()
        );

        if (!trainsFile.exists()) {
            throw new IOException(
                    "Train database file not found: "
                            + trainsFile.getAbsolutePath()
            );
        }

        trainList = objectMapper.readValue(
                trainsFile,
                new TypeReference<List<Train>>() {
                }
        );

        if (trainList == null) {
            throw new IOException("Train list is null.");
        }

        System.out.println(
                "Trains loaded: " + trainList.size()
        );
    }

    public List<Train> searchTrains(
            String source,
            String destination) {

        if (source == null || destination == null) {
            return new ArrayList<>();
        }

        return trainList.stream()
                .filter(train ->
                        validTrain(
                                train,
                                source,
                                destination
                        )
                )
                .collect(Collectors.toList());
    }

    private boolean validTrain(
            Train train,
            String source,
            String destination) {

        if (train == null ||
                train.getStations() == null ||
                train.getStations().isEmpty()) {

            return false;
        }

        String sourceStation =
                source.trim();

        String destinationStation =
                destination.trim();

        int sourceIndex = -1;
        int destinationIndex = -1;

        List<String> stations =
                train.getStations();

        for (int i = 0; i < stations.size(); i++) {

            String station = stations.get(i);

            if (station == null) {
                continue;
            }

            if (station.trim()
                    .equalsIgnoreCase(sourceStation)) {

                sourceIndex = i;
            }

            if (station.trim()
                    .equalsIgnoreCase(destinationStation)) {

                destinationIndex = i;
            }
        }

        return sourceIndex != -1
                && destinationIndex != -1
                && sourceIndex < destinationIndex;
    }

    public void addTrain(Train newTrain) {

        if (newTrain == null ||
                newTrain.getTrainId() == null) {

            return;
        }

        Optional<Train> existingTrain =
                trainList.stream()
                        .filter(train ->
                                train.getTrainId() != null
                                        && train.getTrainId()
                                        .equalsIgnoreCase(
                                                newTrain.getTrainId()
                                        )
                        )
                        .findFirst();

        if (existingTrain.isPresent()) {

            updateTrain(newTrain);

        } else {

            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain) {

        if (updatedTrain == null ||
                updatedTrain.getTrainId() == null) {

            return;
        }

        OptionalInt index =
                IntStream.range(0, trainList.size())
                        .filter(i ->
                                trainList.get(i).getTrainId() != null
                                        && trainList.get(i)
                                        .getTrainId()
                                        .equalsIgnoreCase(
                                                updatedTrain.getTrainId()
                                        )
                        )
                        .findFirst();

        if (index.isPresent()) {

            trainList.set(
                    index.getAsInt(),
                    updatedTrain
            );

            saveTrainListToFile();

        } else {

            trainList.add(updatedTrain);
            saveTrainListToFile();
        }
    }

    private void saveTrainListToFile() {

        try {

            File trainsFile =
                    new File(TRAIN_DB_PATH);

            objectMapper.writeValue(
                    trainsFile,
                    trainList
            );

        } catch (IOException e) {

            System.out.println(
                    "Error saving trains: "
                            + e.getMessage()
            );
        }
    }
}