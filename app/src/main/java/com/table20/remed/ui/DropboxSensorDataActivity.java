package com.table20.remed.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.greysonparrelli.permiso.Permiso;
import com.table20.remed.R;
import com.table20.remed.api.SensorDataApi;
import com.table20.remed.charting.HourAxisValueFormatter;
import com.table20.remed.charting.MyMarkerView;
import com.table20.remed.customclass.SensorDataSet;
import com.table20.remed.customclass.SensorValue;
import com.table20.remed.listener.OnGetSensorDataListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.table20.remed.R.id.chart1;

/**
 * Created by noelchew on 22/09/2016.
 */
public class DropboxSensorDataActivity extends AppCompatActivity {

    private static final String TAG = "DropboxActivity";

    private static final String DROPBOX_ID = "d201";

    private Context context;
    ProgressDialog progressDialog;

    LineChart lineChartTemperature, lineChartUV, lineChartMoisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_sensor_data);
        context = this;
        Permiso.getInstance().setActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name) + " - " + "Dropbox Sensor Data");
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");

        lineChartTemperature = (LineChart) findViewById(chart1);
        lineChartUV = (LineChart) findViewById(R.id.chart2);
        lineChartMoisture = (LineChart) findViewById(R.id.chart3);

        getData();
    }

    private void showProgressDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dropbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sensor_data) {
            // start graph activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Permiso.getInstance().setActivity(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Permiso.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData() {


        SensorDataApi.getSensorData(SensorDataApi.SensorType.TEMPERATURE, onGetTemperatureSensorDataListener);
    }

    private OnGetSensorDataListener onGetTemperatureSensorDataListener = new OnGetSensorDataListener() {
        @Override
        public void onCallSuccess(SensorDataSet sensorDataSet) {
            ArrayList<Entry> values = new ArrayList<Entry>();
//            HashMap<Integer, Integer> timestampHashMap = new HashMap<Integer, Integer>();
            ArrayList<SensorValue> newSensorValues = new ArrayList<>();
            newSensorValues = sensorDataSet.getValues();
            Collections.reverse(newSensorValues);

            long reference_timestamp = newSensorValues.get(0).getTimestampInMilliseconds();
            for (SensorValue sensorValue : newSensorValues) {
//                int count = 1;
//                int tstamp = roundDownTimestampToNearestMultiple(moment.getTstamp(), NEAREST_MULTIPLE_PLOT);
//                Log.d(TAG, "Timestamp calculated: " + tstamp);
//                if (timestampHashMap.containsKey(tstamp)) {
//                    count = timestampHashMap.get(tstamp);
//                    count++;
//                }
//                timestampHashMap.put(tstamp, count);
                values.add(new Entry(sensorValue.getTimestampInMilliseconds() - reference_timestamp, sensorValue.getValue()));
            }

//            values.add(new Entry(0, 0));
//            Map<Integer, Integer> map = new TreeMap<Integer, Integer>(timestampHashMap);
//            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//                values.add(new Entry(entry.getKey(), entry.getValue()));
//            }
//                for (Map.Entry<Integer, Integer> entry : timestampHashMap.entrySet()) {
//                    values.add(new Entry(entry.getKey(), entry.getValue()));
//                }
//            values.add(new Entry(convertToNearestThousands(emVideoView.getDuration()), 0));
//                Collections.sort(values, new EntryXComparator());

//            Log.d(TAG, "Last point location: " + convertToNearestThousands(emVideoView.getDuration()));
            LineDataSet set1;
            if (lineChartTemperature.getData() != null &&
                    lineChartTemperature.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) lineChartTemperature.getData().getDataSetByIndex(0);
                set1.setValues(values);
                lineChartTemperature.getData().notifyDataChanged();
                lineChartTemperature.notifyDataSetChanged();
                lineChartTemperature.invalidate();
            } else {
                // create a dataset and give it a type
                set1 = new LineDataSet(values, "DataSet 1");

                // set the line to be drawn like this "- - - - - -"
//                    set1.enableDashedLine(10f, 5f, 0f);
//                    set1.enableDashedHighlightLine(10f, 5f, 0f);
                set1.setColor(Color.RED);
                set1.setCircleColor(Color.RED);
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);
                set1.setDrawCircleHole(false);
//                    set1.setValueTextSize(9f);
                set1.disableDashedLine();
//                set1.setDrawFilled(true);
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                set1.setHighlightEnabled(false);
                set1.setDrawValues(false);

//                    set1.setFormLineWidth(1f);
//                    set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//                    set1.setFormSize(15.f);

                if (Utils.getSDKInt() >= 18) {
                    // fill drawable only supported on api level 18 and above
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
                    set1.setFillDrawable(drawable);
                } else {
                    set1.setFillColor(Color.BLACK);
                }

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.add(set1); // add the datasets

                // create a data object with the datasets
                LineData data = new LineData(dataSets);

                // set data
                lineChartTemperature.setData(data);

                Legend l = lineChartTemperature.getLegend();
                l.setEnabled(false);

//                lineChartTemperature.getAxisLeft().setEnabled(false);
//                lineChartTemperature.getAxisRight().setEnabled(false);
//                lineChartTemperature.getXAxis().setEnabled(false);
                lineChartTemperature.setDescription("Temperature");

                AxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(reference_timestamp);
                XAxis xAxis = lineChartTemperature.getXAxis();
                xAxis.setValueFormatter(xAxisFormatter);
                MyMarkerView myMarkerView = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view, reference_timestamp);
                lineChartTemperature.setMarkerView(myMarkerView);
            }
        }

        @Override
        public void onCallFailed(final String errorMessage) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });

        }
    };


}

