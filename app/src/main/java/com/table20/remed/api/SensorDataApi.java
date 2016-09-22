package com.table20.remed.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.table20.remed.customclass.SensorDataSet;
import com.table20.remed.listener.OnGetSensorDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by noelchew on 22/09/2016.
 */

public class SensorDataApi {

    private static final String TAG = "SensorDataApi";

//    private static final String API_KEY = "69065c63a72f50e3df1e1c99e42ac09f";
//    private static final String DEVICE_ID = "a7cdcd9c60d9aa0e786f004ac48fb75c";
    private static final String API_KEY = "7ad5dc35a82080247d1f443a8d6210af";
    private static final String DEVICE_ID = "e339458b9bbc9b844d1ce34146219c59";

//    curl --request GET --header "Content-Type: application/json" --header "X-M2X-KEY: 69065c63a72f50e3df1e1c99e42ac09f" 'http://api-m2x.att.com/v2/devices/a7cdcd9c60d9aa0e786f004ac48fb75c/streams/Temperature/values?pretty=true'
//
//    curl --request GET --header "Content-Type: application/json" --header "X-M2X-KEY: 69065c63a72f50e3df1e1c99e42ac09f" 'http://api-m2x.att.com/v2/devices/a7cdcd9c60d9aa0e786f004ac48fb75c/streams/UV/values?pretty=true'
//
//    curl --request GET --header "Content-Type: application/json" --header "X-M2X-KEY: 69065c63a72f50e3df1e1c99e42ac09f" 'http://api-m2x.att.com/v2/devices/a7cdcd9c60d9aa0e786f004ac48fb75c/streams/Moisture/values?pretty=true'
//
//    curl --request GET --header "Content-Type: application/json" --header "X-M2X-KEY: 69065c63a72f50e3df1e1c99e42ac09f" 'http://api-m2x.att.com/v2/devices/a7cdcd9c60d9aa0e786f004ac48fb75c/streams/Latitude/values?pretty=true'
//
//    curl --request GET --header "Content-Type: application/json" --header "X-M2X-KEY: 69065c63a72f50e3df1e1c99e42ac09f" 'http://api-m2x.att.com/v2/devices/a7cdcd9c60d9aa0e786f004ac48fb75c/streams/Longitude/values?pretty=true'

    public enum SensorType {
        TEMPERATURE("Temperature"),
        UV("UV"),
        MOISTURE("Moisture");

        private String name;

        SensorType(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    public static void getSensorData(SensorType sensorType, final OnGetSensorDataListener listener) {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://api-m2x.att.com/v2/devices/" + DEVICE_ID + "/streams/" + sensorType.getName() + "/values?pretty=true'")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-M2X-KEY", API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onCallFailed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                if (!response.isSuccessful()) {
                    listener.onCallFailed(body);
                } else {
                    if (!TextUtils.isEmpty(body)) {
                        Log.d(TAG, "Sensor Data: " + body);
                        SensorDataSet sensorDataSet = new Gson().fromJson(body, SensorDataSet.class);
                        listener.onCallSuccess(sensorDataSet);
                    } else {
                        listener.onCallFailed("Unknown error occurred.");
                    }
                }
            }
        });
    }




}
