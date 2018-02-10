package com.spyder.app.activitys.view.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.util.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RaviKiran Yadav on 2/9/2018.
 */

public class LocationDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "spyder";

    // Location table name
    private static final String TABLE_LOCATION_DATA = "LocationDataTable";

    // Location Table Columns names
    private static final String KEY_Id = "key_id";
    private static final String User_Id = "id";
    private static final String User_Lattitude = "lattitude";
    private static final String User_Langitude = "langitude";
    private static final String User_TimeStamp = "timestamp";

    public LocationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATION_DATA + "(" + KEY_Id + " INTEGER PRIMARY KEY,"
                + User_Id + " INTEGER," + User_Lattitude + " TEXT,"+User_Langitude + " TEXT,"
                + User_TimeStamp + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_DATA);

        // Create tables again
        onCreate(db);
    }
    // Adding new contact
    public void addLocationDetails(LocationDetail locationDetail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User_Id,locationDetail.getUserId());
        values.put(User_Lattitude, locationDetail.getLattitude());
        values.put(User_Langitude, locationDetail.getLongitude());
        values.put(User_TimeStamp, locationDetail.getTimestamp());


        // Inserting Row
        db.insert(TABLE_LOCATION_DATA, null, values);
        db.close(); // Closing database connection
    }
    // Getting single Location
    public LocationDetail getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATION_DATA, new String[] { User_Id,
                        User_Lattitude, User_Langitude,User_TimeStamp }, User_Id + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        LocationDetail locationDetail = new LocationDetail(String.valueOf(Integer.parseInt(cursor.getString(0))),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return locationDetail;
    }

    // Getting All Location Values
    public List<LocationDetail> getALLLocationData() {
        List<LocationDetail> locationDataList = new ArrayList<LocationDetail>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationDetail locationDetail = new LocationDetail();
                locationDetail.setUserId(String.valueOf(Integer.parseInt(cursor.getString(1))));
                locationDetail.setLattitude(cursor.getString(2));
                locationDetail.setLongitude(cursor.getString(3));
                locationDetail.setTimestamp(cursor.getString(4));


                // Adding contact to list
                locationDataList.add(locationDetail);
            } while (cursor.moveToNext());
        }

        // return contact list
        return locationDataList;
    }
}