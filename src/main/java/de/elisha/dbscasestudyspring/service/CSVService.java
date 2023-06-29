package de.elisha.dbscasestudyspring.service;

import de.elisha.dbscasestudyspring.model.TrainStationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {
    @Value("${dbcasestudyspring.trainStationCSVFilePath}")
    private String pathOfTrainStationFile;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CSVService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<TrainStationModel> readCSVFile() {
        List<TrainStationModel> trainStations = new ArrayList<>();

        try {
            Resource resource = resourceLoader.getResource(pathOfTrainStationFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                TrainStationModel station = createTrainStationModel(fields);
                trainStations.add(station);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trainStations;
    }

    private TrainStationModel createTrainStationModel(String[] fields) {
        TrainStationModel station = new TrainStationModel();
        station.setDS100(fields[1]);
        station.setName(fields[3]);
        station.setType(fields[4]);
        if (!fields[5].isEmpty()) {
            station.setLongitude(Double.parseDouble(fields[5].replace(",", ".")));
        }
        if (!fields[6].isEmpty()) {
            station.setLatitude(Double.parseDouble(fields[6].replace(",", ".")));
        }
        return station;
    }
}
