package com.table20.remed.customclass;

import java.util.ArrayList;

/**
 * Created by noelchew on 23/09/2016.
 */

public class SensorDataSet {
    private int limit;
    private String end;
    private ArrayList<SensorValue> values;

    public SensorDataSet(int limit, String end, ArrayList<SensorValue> values) {
        this.limit = limit;
        this.end = end;
        this.values = values;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ArrayList<SensorValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<SensorValue> values) {
        this.values = values;
    }
}
