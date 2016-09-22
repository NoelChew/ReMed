package com.table20.remed.customclass;

/**
 * Created by noelchew on 22/09/2016.
 */

public class SensorData {
    private long timestamp;
    private int temperature;
    private double uv;
    private double moisture;

    public SensorData() {

    }

    public SensorData(long timestamp, int temperature, double uv, double moisture) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.uv = uv;
        this.moisture = moisture;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getUv() {
        return uv;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }
}
