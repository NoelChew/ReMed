package com.table20.remed.customclass;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by noelchew on 22/09/2016.
 */

public class SensorValue {
    private String timestamp;
    private float value;

    public SensorValue(String timestamp, float value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public long getTimestampInMilliseconds() {
        DateTime dateTime = new DateTime(getTimestamp(), DateTimeZone.UTC);
        return dateTime.getMillis();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
