package com.spyder.app.activitys.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class SharedPref {
    private static final String DCPREFERENCES = "DcPrefs";
    private static String KEY_IS_LOGGEIN = "isLoggeIn";
    private static final String USER_ID = "userid";
    private static SharedPreferences mPref;
    private static SharedPreferences.Editor editor;
    private static String TAG = SharedPref.class.getSimpleName();
    Context _context;
    int PRIVATE_MODE = 0;
    public SharedPref(Context context) {
        this._context = context;
        mPref = _context.getSharedPreferences(DCPREFERENCES, PRIVATE_MODE);
        editor = mPref.edit();
    }

    public void setUserId(String id){
        editor.putString(USER_ID,id);
        editor.commit();
    }
    public String getUserId() {
        return mPref.getString(USER_ID, "");
    }
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public boolean isLoggedIn() {
        return mPref.getBoolean(KEY_IS_LOGGEIN, false);
    }

    public  void clearprf(){
        editor=mPref.edit();
        editor.clear();
        editor.commit();

    }
}
