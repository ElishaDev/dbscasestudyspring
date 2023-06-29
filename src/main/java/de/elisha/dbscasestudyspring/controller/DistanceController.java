package de.elisha.dbscasestudyspring.controller;

import de.elisha.dbscasestudyspring.exception.LongDistanceTrafficStationNotFoundException;
import de.elisha.dbscasestudyspring.model.DistanceResponse;
import de.elisha.dbscasestudyspring.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/distance")
public class DistanceController {
    private static final DistanceResponse ERROR_RESPONSE = new DistanceResponse();
    @Autowired
    private DistanceService distanceService;

    @GetMapping(path = "/{stationA}/{stationB}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DistanceResponse getDistance(@PathVariable("stationA") String stationA,
                                        @PathVariable("stationB") String stationB) {
        try {
            return distanceService.getDistanceBetweenTwoStations(stationA, stationB);
        } catch (LongDistanceTrafficStationNotFoundException e) {
            return ERROR_RESPONSE;
        }
    }
}