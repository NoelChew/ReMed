package com.table20.remed.customclass;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by noelchew on 22/09/2016.
 */

public class User {
    private String id;
    private double latitude;
    private double longitude;
    private String email;
    private String fcmToken;
    private HashMap<String, Medicine> medicines;

    public User() {

    }

    public User(String id, double latitude, double longitude, String email, String fcmToken) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.fcmToken = fcmToken;
    }

    public User(String id, double latitude, double longitude, String email, String fcmToken, HashMap<String, Medicine> medicines) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.fcmToken = fcmToken;
        this.medicines = medicines;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public HashMap<String, Medicine> getMedicines() {
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
