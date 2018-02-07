package com.spyder.app.activitys.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Srisailam Paka on 13-07-2017.
 */

public class MyLog {
    public static final boolean DEBUG = true;

    public static void log(String TAG, String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission( context, Manifest.permission.READ_EXTERNAL_STORAGE +
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA +
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE +
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)))) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)||
                                ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_PHONE_STATE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permissions");
                    alertBuilder.setMessage("permissions is necessary for this App");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    public static String changeDate(String serverdate){
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy";
        //String outputPattern = "dd-MMM-yyyy h:mm a";
        String month="MMM";
        String time="h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.US);
        SimpleDateFormat inputFormat1 = new SimpleDateFormat(month, Locale.US);
        SimpleDateFormat outputFormat1 = new SimpleDateFormat(time, Locale.US);

        Date date = null;
        String dateformat = null;
        String m = null;
        String t = null;

        try {
            date = inputFormat.parse(serverdate);
            dateformat = outputFormat.format(date);
            m = inputFormat1.format(date);
            t = outputFormat1.format(date);
            Log.d("narendra",dateformat);
            Log.d("month",m+" "+"time"+t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String format=dateformat.replace("-","");
        Log.d("narendra1",format);

        return dateformat;
    }
}


