package com.table20.remed.listener;

import com.table20.remed.customclass.SensorDataSet;

/**
 * Created by noelchew on 23/09/2016.
 */

public interface OnGetSensorDataListener {

    void onCallSuccess(SensorDataSet sensorDataSet);

    void onCallFailed(String errorMessage);

}
