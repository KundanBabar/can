package com.gt.gtProject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gt.gtProject.service.GpsDeviceService;

@RestController
@RequestMapping("/api/gps")
public class GPSController {
	
	private volatile double latitude = 0.0;
    private volatile double longitude = 0.0;


    @Autowired
    private GpsDeviceService gpsDeviceService;

    @GetMapping("/data") 
    public GPSResponse getGPSData() {
    	try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Listening for GPS device on port 8080...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("GPS Device Connected.");

                // Handle incoming GPS messages
                handleGPSData(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GPSResponse(gpsDeviceService.getLatitude(), gpsDeviceService.getLongitude());
    }
    
    private void handleGPSData(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseGPSData(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseGPSData(String gpsData) {
        // Simulate parsing logic here (parse NMEA strings to extract GPS coordinates)
        System.out.println("Received GPS Data: " + gpsData);

        if (gpsData.contains("$GPRMC")) {
            String[] parts = gpsData.split(",");
            try {
                String latString = parts[3];
                String latDirection = parts[4];
                String lonString = parts[5];
                String lonDirection = parts[6];

                latitude = parseCoordinate(latString, latDirection);
                longitude = parseCoordinate(lonString, lonDirection);

                System.out.println("Parsed Latitude: " + latitude + " Longitude: " + longitude);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private double parseCoordinate(String value, String direction) {
        double coord = Double.parseDouble(value);
        if (direction.equals("S") || direction.equals("W")) {
            coord = -coord;
        }
        return coord;
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
