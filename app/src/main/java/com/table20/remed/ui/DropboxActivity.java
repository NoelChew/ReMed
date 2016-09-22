package com.table20.remed.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.greysonparrelli.permiso.Permiso;
import com.table20.remed.R;
import com.table20.remed.adapter.MedicineAdapter;
import com.table20.remed.customclass.Dropbox;
import com.table20.remed.customclass.Medicine;

import java.util.ArrayList;

/**
 * Created by noelchew on 22/09/2016.
 */
public class DropboxActivity extends AppCompatActivity {

    private static final String TAG = "DropboxActivity";

    private static final String USER_EMAIL = "abc@gmail.com";
    private static final double USER_LATITUDE = 3.154205;
    private static final double USER_LONGITUDE = 101.713112;

    private static final String DROPBOX_ID = "d201";

    private Context context;
    ProgressDialog progressDialog;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Medicine> medicineArrayList;

    private static final int DECODE_QR_REQUEST_CODE = 1201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        context = this;
        Permiso.getInstance().setActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name) + " - " + "Dropbox");
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");

        medicineArrayList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MedicineAdapter(medicineArrayList, context);
        mRecyclerView.setAdapter(mAdapter);

        getData();
    }

    private void getData() {
        getDropboxDataFromFirebase();

    }

    private void getDropboxDataFromFirebase() {
        showProgressDialog();
        FirebaseDatabase.getInstance().getReference("dropbox").child(DROPBOX_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "dropbox ID: " + DROPBOX_ID + "\ndata: " + dataSnapshot.toString());
                Dropbox dropbox = dataSnapshot.getValue(Dropbox.class);
                if (dropbox == null || dropbox.getMedicines() == null || dropbox.getMedicineCount() == 0) {
                    medicineArrayList = new ArrayList<Medicine>();
                } else {
                    medicineArrayList = dropbox.getMedicineArrayList();
                }
                mAdapter = new MedicineAdapter(medicineArrayList, context);
                mRecyclerView.setAdapter(mAdapter);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
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
            Intent intent = new Intent(context, DropboxSensorDataActivity.class);
            startActivity(intent);
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
}

