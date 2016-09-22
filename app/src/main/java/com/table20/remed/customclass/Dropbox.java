package com.table20.remed.customclass;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by noelchew on 22/09/2016.
 */

public class Dropbox {
    private String id;
    private double latitude;
    private double longitude;
    private boolean activeState;
    private HashMap<String, SensorData> sensorData;
    private HashMap<String, Medicine> medicines;

    public Dropbox() {

    }

    public Dropbox(String id, double latitude, double longitude, boolean activeState) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.activeState = activeState;
    }

    public Dropbox(String id, double latitude, double longitude, boolean activeState, HashMap<String, SensorData> sensorData) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.activeState = activeState;
        this.sensorData = sensorData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isActiveState() {
        return activeState;
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public HashMap<String, SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(HashMap<String, SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    @Exclude
    public ArrayList<SensorData> getSensorDataArrayList() {
        ArrayList<SensorData> sensorDataArrayList = new ArrayList<>();
        for (Map.Entry<String, SensorData> entry : sensorData.entrySet()) {
            sensorDataArrayList.add(entry.getValue());
        }
        return sensorDataArrayList;
    }

    @Exclude
    public int getSensorDataCount() {
        if (sensorData != null && !sensorData.isEmpty()) {
            return sensorData.size();
        } else {
            return 0;
        }
    }public HashMap<String, Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(HashMap<String, Medicine> medicines) {
        this.medicines = medicines;
    }

    @Exclude
    public ArrayList<Medicine> getMedicineArrayList() {
        ArrayList<Medicine> medicines1 = new ArrayList<>();
        for (Map.Entry<String, Medicine> entry : medicines.entrySet()) {
            medicines1.add(entry.getValue());
        }
        return medicines1;
    }

    @Exclude
    public int getMedicineCount() {
        if (medicines != null && !medicines.isEmpty()) {
            return medicines.size();
        } else {
            return 0;
        }
    }
}
