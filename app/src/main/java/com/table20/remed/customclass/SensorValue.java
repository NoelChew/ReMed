package com.table20.remed.customclass;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

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
        DateTime dateTime = new DateTime(getTimestamp(), DateTimeZone.forTimeZone(TimeZone.getTimeZone("MY")));
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
