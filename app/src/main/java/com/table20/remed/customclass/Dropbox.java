package com.table20.remed.customclass;

import java.util.HashMap;

/**
 * Created by noelchew on 22/09/2016.
 */

public class Dropbox {
    private String id;
    private double latitude;
    private double longitude;
    private boolean activeState;
    private HashMap<String, SensorData> sensorData;

    

    public Dropbox(String id, double latitude, double longitude, boolean activeState, HashMap<String, SensorData> sensorData) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.activeState = activeState;
        this.sensorData = sensorData;
    }

}
