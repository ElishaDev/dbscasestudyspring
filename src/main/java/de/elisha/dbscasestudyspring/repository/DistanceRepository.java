package de.elisha.dbscasestudyspring.repository;

import de.elisha.dbscasestudyspring.exception.LongDistanceTrafficStationNotFoundException;
import de.elisha.dbscasestudyspring.service.CSVService;
import de.elisha.dbscasestudyspring.model.TrainStationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistanceRepository {
    private static final String LONG_DISTANCE_TRAFFIC_TRAIN_STATION_TYPE = "FV";
    private final CSVService csvService;
    private final List<TrainStationModel> trainStationList;

    @Autowired
    public DistanceRepository(CSVService csvService) {
        this.csvService = csvService;
        this.trainStationList = initializeTrainStations();
    }

    public TrainStationModel findTrainStationByDS100(String ds100) {
        return trainStationList.stream()
                .filter(trainStation -> matchesDS100(trainStation, ds100))
                .filter(this::isLongDistanceTrafficStation)
                .findFirst()
                .orElseThrow(LongDistanceTrafficStationNotFoundException::new);
    }

    private boolean matchesDS100(TrainStationModel trainStation, String ds100) {
        return trainStation.getDS100().equals(ds100);
    }

    private boolean isLongDistanceTrafficStation(TrainStationModel trainStation) {
        return trainStation.getType().equals(LONG_DISTANCE_TRAFFIC_TRAIN_STATION_TYPE);
    }

    private List<TrainStationModel> initializeTrainStations() {
        return csvService.readCSVFile();
    }
}
