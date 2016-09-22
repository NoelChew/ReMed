package com.table20.remed.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.greysonparrelli.permiso.Permiso;
import com.noelchew.permisowrapper.PermisoWrapper;
import com.table20.remed.R;
import com.table20.remed.adapter.MedicineAdapter;
import com.table20.remed.customclass.Dropbox;
import com.table20.remed.customclass.Medicine;
import com.table20.remed.customclass.ScanAction;
import com.table20.remed.customclass.User;
import com.table20.remed.data.MedicineData;
import com.table20.remed.user.HackathonUserModule;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String USER_EMAIL = "abc@gmail.com";
    private static final double USER_LATITUDE = 3.154205;
    private static final double USER_LONGITUDE = 101.713112;

    private Context context;
    String userId;

    ProgressDialog progressDialog;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Medicine> medicineArrayList;

    private static final int DECODE_QR_REQUEST_CODE = 1201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        Permiso.getInstance().setActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                PermisoWrapper.getPermissionTakePicture(context, new PermisoWrapper.PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(context, DecoderActivity.class);
                        startActivityForResult(intent, DECODE_QR_REQUEST_CODE);
                    }

                    @Override
                    public void onPermissionDenied() {
                        Snackbar.make(view, "Permission to use CAMERA denied.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            }
        });

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
        userId = HackathonUserModule.getUserId();
        getUserDataFromFirebase();

    }

    private void getUserDataFromFirebase() {
        showProgressDialog();
        FirebaseDatabase.getInstance().getReference("user").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "User ID: " + userId + "\ndata: " + dataSnapshot.toString());
                User user = dataSnapshot.getValue(User.class);
                if (user == null || user.getMedicines() == null || user.getMedicineCount() == 0) {
                    medicineArrayList = new ArrayList<Medicine>();
                } else {
                    medicineArrayList = user.getMedicineArrayList();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reset_data) {
            final ArrayList<Medicine> medicineArrayList1 = MedicineData.getMedicineArrayList();
            final User user = new User(HackathonUserModule.getUserId(), USER_LATITUDE, USER_LONGITUDE, USER_EMAIL, "n/a");
            FirebaseDatabase.getInstance().getReference("user").child(user.getId()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    for (Medicine medicine : medicineArrayList1) {
//                        String key = FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("medicines").push().getKey();
                        FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("medicines").child(medicine.getId()).setValue(medicine);
                    }
                    Dropbox dropbox = new Dropbox("d201", USER_LATITUDE, USER_LONGITUDE, true);
                    FirebaseDatabase.getInstance().getReference("dropbox").child(dropbox.getId()).setValue(dropbox);
                }
            });
            return true;
        } else if (id == R.id.test) {
//            Medicine medicine = new Medicine("101", "Medicine A", "https://dummyimage.com/400x400/212121/fff.jpg&text=Medicine+A", "JAN 2020");
//            ScanAction scanAction = new ScanAction(medicine);
            Dropbox dropbox = new Dropbox("d201", USER_LATITUDE, USER_LONGITUDE, true);
            ScanAction scanAction = new ScanAction(dropbox);
            String json = new Gson().toJson(scanAction, ScanAction.class);
            Log.d(TAG, "scanAction json: " + json);
            // {"expiryDate":"JAN 2020","id":"101","imageUrl":"https://dummyimage.com/400x400/212121/fff.jpg\u0026text\u003dMedicine+A","name":"Medicine A"}
// scanAction json: {"medicine":{"expiryDate":"JAN 2020","id":"101","imageUrl":"https://dummyimage.com/400x400/212121/fff.jpg\u0026text\u003dMedicine+A","name":"Medicine A"},"scanType":0}
            // scanAction json: {"dropbox":{"activeState":true,"id":"201","latitude":3.154205,"longitude":101.713112},"scanType":1}


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
        if (requestCode == DECODE_QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra(DecoderActivity.DATA_KEY);
                Log.d(TAG, "text: " + text);
//                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(text)) {
                    ScanAction scanAction = new Gson().fromJson(text, ScanAction.class);
                    if (scanAction.isMedicine()) {
                        // add into user list
//                        String key = FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").push().getKey();
                        FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").child(scanAction.getMedicine().getId()).setValue(scanAction.getMedicine());
                    } else {
                        // add into Dropbox
                        FirebaseDatabase.getInstance().getReference("dropbox").child(scanAction.getDropbox().getId()).child("medicines").child(scanAction.getMedicine().getId()).setValue(scanAction.getMedicine());
                        // remove from user list
                        FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").child(scanAction.getMedicine().getId()).removeValue();
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
