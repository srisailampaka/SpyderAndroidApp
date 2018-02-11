package com.spyder.app.activitys.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.view.activities.Browser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class GetDetailsInformation {
    private Context context;
    private SharedPref sharedPref;
    public GetDetailsInformation(Context applicationContext) {
        context = applicationContext;
        sharedPref = new SharedPref(context);
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

        Cursor mCursor = context.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,
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
            callhistory.setPhoneNumber(sharedPref.getUserId());
            callhistory.setPhoneNumber(phnumber);
            callhistory.setDuration(callduration);
            callhistory.setTypeOfCall(callTypeStr);
            callhistory.setTimestamp(calldate);
            saveCallHistoryDetailsList.add(callhistory);
        }
        MyLog.log("calldata......",sb.toString());
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
}
