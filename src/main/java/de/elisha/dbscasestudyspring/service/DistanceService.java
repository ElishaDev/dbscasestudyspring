package de.elisha.dbscasestudyspring.service;

import de.elisha.dbscasestudyspring.model.DistanceResponse;
import de.elisha.dbscasestudyspring.model.TrainStationModel;
import de.elisha.dbscasestudyspring.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    private static final String DEFAULT_DISTANCE_UNIT = "km";
    private static final double RADIUS_OF_EARTH_IN_KM = 6371;
    @Autowired
    private DistanceRepository distanceRepository;

    public DistanceResponse getDistanceBetweenTwoStations(String stationA, String stationB) {
        TrainStationModel trainStationA = distanceRepository.findTrainStationByDS100(stationA);
        TrainStationModel trainStationB = distanceRepository.findTrainStationByDS100(stationB);
        int roundedDistance = calculateDistance(trainStationA, trainStationB);
        return buildDistanceResponse(trainStationA, trainStationB, roundedDistance);
    }

    private DistanceResponse buildDistanceResponse(TrainStationModel trainStationA, TrainStationModel trainStationB, int roundedDistanceInKm) {
        DistanceResponse response = new DistanceResponse();
        response.setFrom(trainStationA.getName());
        response.setTo(trainStationB.getName());
        response.setDistance(roundedDistanceInKm);
        response.setUnit(DEFAULT_DISTANCE_UNIT);
        return response;
    }

    private int calculateDistance(TrainStationModel trainStationA, TrainStationModel trainStationB) {
        double fromLat = trainStationA.getLatitude();
        double fromLon = trainStationA.getLongitude();
        double toLat = trainStationB.getLatitude();
        double toLon = trainStationB.getLongitude();
        return applyHaversineMethod(fromLat, fromLon, toLat, toLon);
    }

    private int applyHaversineMethod(double fromLat, double fromLon, double toLat, double toLon) {
        double latDiff = Math.toRadians(toLat - fromLat);
        double lonDiff = Math.toRadians(toLon - fromLon);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(fromLat)) * Math.cos(Math.toRadians(toLat))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = RADIUS_OF_EARTH_IN_KM * c;
        return (int) Math.round(distance);
    }
}
