package com.gt.gtProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.gtProject.service.GpsDeviceService;

@RestController
@RequestMapping("/api/gps")
public class GPSController {

    @Autowired
    private GpsDeviceService gpsDeviceService;

    @GetMapping("/data") 
    public GPSResponse getGPSData() {
        return new GPSResponse(gpsDeviceService.getLatitude(), gpsDeviceService.getLongitude());
    }
}

class GPSResponse {
    private double latitude;
    private double longitude;

    public GPSResponse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
