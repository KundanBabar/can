package com.gt.gtProject.model;

public class GPSData {
    private double latitude;
    private double longitude;

    // Constructor
    public GPSData(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

