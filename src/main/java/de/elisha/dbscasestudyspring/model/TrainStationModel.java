package de.elisha.dbscasestudyspring.model;

public class TrainStationModel {
    private String DS100;
    private String Name;
    private String Type;
    private Double Longitude;
    private Double Latitude;

    public String getDS100() {
        return DS100;
    }

    public void setDS100(String DS100) {
        this.DS100 = DS100;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }
}