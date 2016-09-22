package com.table20.remed.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.gson.Gson;
import com.table20.remed.R;
import com.table20.remed.customclass.Dropbox;
import com.table20.remed.customclass.ScanAction;

/**
 * Created by noelchew on 22/09/2016.
 */

public class DecoderActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String TAG = "DecoderActivity";

    public static final String DATA_KEY = "dataKey";
    private Context context;
    private QRCodeReaderView mydecoderview;

    private boolean isDonateMode = false;
    private Dropbox dropbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        context = this;
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mydecoderview.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        mydecoderview.setTorchEnabled(true);

        // Use this function to set front camera preview
//        mydecoderview.setFrontCamera();

        // Use this function to set back camera preview
        mydecoderview.setBackCamera();

        isDonateMode = false;
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Log.d(TAG, "onQRCodeRead: " + text);

        if (!TextUtils.isEmpty(text)) {
            ScanAction scanAction = new Gson().fromJson(text, ScanAction.class);
            if (!isDonateMode) {
                if (scanAction.isMedicine()) {
                    Intent intent = new Intent();
                    intent.putExtra(DATA_KEY, text);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mydecoderview.stopCamera();
                    dropbox = scanAction.getDropbox();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Scan Medicine")
                            .setMessage("Please scan medicine to be donated.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isDonateMode = true;
                                    mydecoderview.startCamera();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            } else {
                // scan again
                if (scanAction.isMedicine()) {
                    scanAction.setDropbox(dropbox);
                    scanAction.setScanType(ScanAction.TYPE_DROPBOX);
                    Intent intent = new Intent();
                    intent.putExtra(DATA_KEY, new Gson().toJson(scanAction));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mydecoderview.stopCamera();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Scan Error")
                            .setMessage("Please scan medicine to be donated.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isDonateMode = true;
                                    mydecoderview.startCamera();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        }






//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }
}
