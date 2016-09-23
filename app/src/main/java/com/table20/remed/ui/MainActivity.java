package com.table20.remed.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
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
import com.table20.remed.api.JupyterDataApi;
import com.table20.remed.customclass.Dropbox;
import com.table20.remed.customclass.Medicine;
import com.table20.remed.customclass.ScanAction;
import com.table20.remed.customclass.User;
import com.table20.remed.data.MedicineData;
import com.table20.remed.listener.OnCallApiGeneralListener;
import com.table20.remed.listener.OnItemClickListener;
import com.table20.remed.user.HackathonUserModule;
import com.table20.remed.util.AnimationUtil;
import com.table20.remed.util.DateUtil;
import com.table20.remed.util.GoogleMapUtil;

import java.util.ArrayList;
import java.util.Date;

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

    private View fab;
    private BottomSheetLayout bottomSheetLayout;

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

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (bottomSheetLayout.isSheetShowing()) {
                    return;
                }
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
        mAdapter = new MedicineAdapter(medicineArrayList, context, medicineOnItemClickListener);
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
                mAdapter = new MedicineAdapter(medicineArrayList, context, medicineOnItemClickListener);
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
        if (id == R.id.dropbox_version) {
            Intent intent = new Intent(context, DropboxActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.reset_data) {
            final ArrayList<Medicine> medicineArrayList1 = MedicineData.getMedicineArrayList1(context);
            final ArrayList<Medicine> medicineArrayList2 = MedicineData.getMedicineArrayList2(context);
            final User user = new User(HackathonUserModule.getUserId(), USER_LATITUDE, USER_LONGITUDE, USER_EMAIL, "n/a");
            FirebaseDatabase.getInstance().getReference("user").child(user.getId()).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    for (Medicine medicine : medicineArrayList1) {
//                        String key = FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("medicines").push().getKey();
                        FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("medicines").child(medicine.getId()).setValue(medicine);
                    }
                    final Dropbox dropbox = new Dropbox("d201", USER_LATITUDE, USER_LONGITUDE, true);
                    FirebaseDatabase.getInstance().getReference("dropbox").child(dropbox.getId()).setValue(dropbox, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            for (Medicine medicine : medicineArrayList2) {
//                        String key = FirebaseDatabase.getInstance().getReference("user").child(user.getId()).child("medicines").push().getKey();
                                FirebaseDatabase.getInstance().getReference("dropbox").child(dropbox.getId()).child("medicines").child(medicine.getId()).setValue(medicine);
                            }
                        }
                    });

                }
            });
            return true;
        } else if (id == R.id.test) {
//            Medicine medicine = new Medicine("m101", "Panadol", getString(R.string.med0_image_url), new Date().getTime(), DateUtil.getDateFromString("30/10/2016", "dd/MM/yyyy").getTime());
//            Medicine medicine = new Medicine("m102", "Arcoxia", getString(R.string.med2_image_url), new Date().getTime(), DateUtil.getDateFromString("30/11/2017", "dd/MM/yyyy").getTime());
            Medicine medicine = new Medicine("m103", "Advil", getString(R.string.med1_image_url), new Date().getTime(), DateUtil.getDateFromString("30/12/2018", "dd/MM/yyyy").getTime());
            ScanAction scanAction = new ScanAction(medicine);
//            Dropbox dropbox = new Dropbox("d201", USER_LATITUDE, USER_LONGITUDE, true);
//            ScanAction scanAction = new ScanAction(dropbox);
            String json = new Gson().toJson(scanAction, ScanAction.class);
            Log.d(TAG, "scanAction json: " + json);
            // scanAction json: {"medicine":{"expiryDate":1598716800000,"id":"m101","imageUrl":"https://firebasestorage.googleapis.com/v0/b/remed-53f87.appspot.com/o/medicine%2Fmed0.png?alt\u003dmedia\u0026token\u003dcaa8410f-ed4d-479b-8d83-69468b7a3a1b","name":"Panadol","purchaseDate":1474599808618},"scanType":0}

// scanAction json: {"medicine":{"expiryDate":1598716800000,"id":"m101","imageUrl":"https://dummyimage.com/400x400/212121/fff.jpg\u0026text\u003dMedicine+A","name":"Medicine A","purchaseDate":1474594773021},"scanType":0}

            // {"expiryDate":"JAN 2020","id":"101","imageUrl":"https://dummyimage.com/400x400/212121/fff.jpg\u0026text\u003dMedicine+A","name":"Medicine A"}
// scanAction json: {"medicine":{"expiryDate":"JAN 2020","id":"m101","imageUrl":"https://dummyimage.com/400x400/212121/fff.jpg\u0026text\u003dMedicine+A","name":"Medicine A"},"scanType":0}
            // scanAction json: {"dropbox":{"activeState":true,"id":"d201","latitude":3.154205,"longitude":101.713112},"scanType":1}


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
                        popupAddMedicine(true, scanAction.getMedicine());
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Thank You")
                                .setMessage("Thank you for donating " + scanAction.getMedicine().getName() + ".")
                                .setPositiveButton("OK", null)
                                .show();

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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    popupExpiryUI();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    popupExpiryUI();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private OnItemClickListener medicineOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(Medicine medicine) {
            popupAddMedicine(false, medicine);
        }
    };

    private void popupAddMedicine(final boolean isAdd, final Medicine medicine) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheet_add_medicine, bottomSheetLayout, false);
        bottomSheetLayout.showWithSheetView(view);
        TextView tvName, tvDetails;
        Button btnAdd, btnCancel;
        ImageView imageView;
        tvName = (TextView) view.findViewById(R.id.text_view_name);
        tvDetails = (TextView) view.findViewById(R.id.text_view_expiry);
        btnAdd = (Button) view.findViewById(R.id.button_add);
        btnCancel = (Button) view.findViewById(R.id.button_cancel);
        imageView = (ImageView) view.findViewById(R.id.image_view_medicine);

        Glide.with(MainActivity.this).load(medicine.getImageUrl()).into(imageView);

        tvName.setText(medicine.getName());
        if (medicine.getExpiryDate() <= new Date().getTime()) {
            // expired
            tvName.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
            tvName.setText(medicine.getName() + " (Expired)");
        } else {
            tvName.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light));
        }


        tvDetails.setText(medicine.getExpiryDateInString());

        if (isAdd) {
            btnAdd.setText("Add");
        } else {
            btnAdd.setText("Remove");
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isAdd) {
                    // add into user list
//                        String key = FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").push().getKey();
                    FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").child(medicine.getId()).setValue(medicine);

                    // add point into Jupyter data set
                    JupyterDataApi.addDataPoint(new OnCallApiGeneralListener() {
                        @Override
                        public void onCallSuccess() {

                        }

                        @Override
                        public void onCallFailed(final String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


                } else {
                    // remove from user list
                    FirebaseDatabase.getInstance().getReference("user").child(userId).child("medicines").child(medicine.getId()).removeValue();
                }
                bottomSheetLayout.dismissSheet();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetLayout.dismissSheet();
            }
        });
        AnimationUtil.animateFadeOut(fab, 100, 300);
        AnimationUtil.animateScaleXY(imageView, 300, 800);
        AnimationUtil.animateScaleXY(btnAdd, 600, 800);
        AnimationUtil.animateScaleXY(btnCancel, 800, 800);

        bottomSheetLayout.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                AnimationUtil.animateFadeIn(fab, 100, 300);
            }
        });

    }

    private void popupExpiryUI() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheet_expired_medicine, bottomSheetLayout, false);
        bottomSheetLayout.showWithSheetView(view);
        TextView tvDetails;
        Button btnGetDirection;
        ImageView ivMap;
        tvDetails = (TextView) view.findViewById(R.id.text_view_details);
        btnGetDirection = (Button) view.findViewById(R.id.button_join_chat);
        ivMap = (ImageView) view.findViewById(R.id.image_view_map);

        ArrayList<Medicine> medicineArrayList = MedicineData.getMedicineArrayList1(context);
        Medicine medicine = medicineArrayList.get(0);
//        Medicine medicine = new Medicine("101", "Medicine A", "https://dummyimage.com/400x400/212121/fff.jpg&text=Medicine+A", "");
        ivMap.setImageResource(R.drawable.expiry_map);
        tvDetails.setText("Your " + medicine.getName() + " is expiring soon.\nWe found a Dropbox nearby!");
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 3.154045;
                double longitude = 101.713018;
                GoogleMapUtil.startGetDirectionActivity(context, latitude, longitude);
            }
        });
        AnimationUtil.animateFadeOut(fab, 100, 300);
        AnimationUtil.animateScaleXY(ivMap, 300, 800);
        AnimationUtil.animateScaleXY(btnGetDirection, 600, 800);

        bottomSheetLayout.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                AnimationUtil.animateFadeIn(fab, 100, 300);
            }
        });

    }
}
