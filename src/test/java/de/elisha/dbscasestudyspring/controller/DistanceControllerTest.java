package de.elisha.dbscasestudyspring.controller;

import de.elisha.dbscasestudyspring.model.DistanceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "dbcasestudyspring.trainStationCSVFilePath=classpath:TestTrainStations.csv")
class DistanceControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getDistance_ShouldRespondWithCorrectData() {
        //Arrange
        String stationA = "FF";
        String stationB = "BLS";
        RequestEntity<Void> request = RequestEntity.get(getAPIBaseUrl() + "/distance/{stationA}/{stationB}", stationA, stationB).build();
        //Act
        ResponseEntity<DistanceResponse> response = restTemplate.getRestTemplate().exchange(request, DistanceResponse.class);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        DistanceResponse distance = response.getBody();
        assertEquals("Frankfurt(Main)Hbf", distance.getFrom());
        assertEquals("Berlin Hbf", distance.getTo());
        assertEquals(423, distance.getDistance());
        assertEquals("km", distance.getUnit());
    }

    @CsvSource(textBlock = """
            # stationA, stationB, expectedDistance
              FF,       BLS,      423
              KA,       AK,       480
              RK,       AH,       519
              BSPD,     KNE,      460
              KKO,      TS,       209
            """)
    @ParameterizedTest
    public void getDistance_ShouldCalculateCorrectDistance(String stationA, String stationB, int expectedDistance) {
        //Arrange
        RequestEntity<Void> request = RequestEntity.get(getAPIBaseUrl() + "/distance/{stationA}/{stationB}", stationA, stationB).build();
        //Act
        ResponseEntity<DistanceResponse> response = restTemplate.getRestTemplate().exchange(request, DistanceResponse.class);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        DistanceResponse distance = response.getBody();
        assertEquals(expectedDistance, distance.getDistance());
        assertEquals("km", distance.getUnit());
    }

    private String getAPIBaseUrl() {
        return "http://localhost:" + port + "/api/v1";
    }
}