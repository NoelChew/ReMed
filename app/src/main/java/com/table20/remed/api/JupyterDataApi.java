package com.table20.remed.api;

import com.table20.remed.listener.OnCallApiGeneralListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by noelchew on 22/09/2016.
 */

public class JupyterDataApi {

    private static final String TAG = "JupyterDataApi";

    public static void addDataPoint(final OnCallApiGeneralListener listener) {
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://54.187.187.198:8000/?pushData=yes")
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
                    listener.onCallSuccess();
                }
            }
        });
    }


}
