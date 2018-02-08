package com.spyder.app.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spyder.app.R;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.CallHistoryDetailsPojo;
import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserDetails_;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.view.activities.BaseActivity;
import com.spyder.app.activitys.webservices.Mediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements SpyderContract.View {

    private Mediator mediator;
    protected SpyderPresenter mSpyderPresenter;
    Gson gson = new Gson();
    private List<LocationDetail> locationDetailList;
    private List<CallHistoryDetailsPojo> saveCallHistoryDetailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediator = Mediator.getInstance(getApplicationContext());
        mSpyderPresenter = new SpyderPresenter(this, getApplicationContext());

      // calluserDetails();
        //callLocationDetails();
       // callSaveCallHistoryDetails();
        callGetCallHistoryDetails();



    }

    @Override
    public void successResponse(BaseContext baseContext) {
        hideProgressDialog();
        MyLog.log("success", "cccccccccccc" + baseContext.getStatus());

    }

    @Override
    public void failureResponse(String error) {
        hideProgressDialog();
        MyLog.log("responce......fail............",error);

    }

    public  void calluserDetails(){

        UserDetails details = new UserDetails();
        UserDetails_ details_ = new UserDetails_();

        details_.setDeviceid("1517502985713");
        details_.setUserId("1");
        details_.setUsername("121305");

        details.setUserDetails(details_);
        MyLog.log("details", gson.toJson(details));
        showProgressDialog();
        mSpyderPresenter.saveUserDetails(details);

    }
    public void callLocationDetails(){

        locationDetailList=new ArrayList<>();
        LocationDetails locationDetails=new LocationDetails();
        LocationDetail locationDetail=new LocationDetail();
        locationDetail.setUserId("121305");
        locationDetail.setTimestamp("1517502985713");
        locationDetail.setLattitude("17.459313");
        locationDetail.setLongitude("78.368570");
        locationDetailList.add(locationDetail);
        locationDetails.setLocationDetails(locationDetailList);

        MyLog.log("locationdetails", gson.toJson(locationDetails));
        showProgressDialog();
        mSpyderPresenter.saveLocationDetails(locationDetails);
    }
    public void callSaveCallHistoryDetails(){

        saveCallHistoryDetailsList=new ArrayList<>();
        CallHistoryDetails callHistoryDetails=new CallHistoryDetails();
        CallHistoryDetailsPojo callHistoryDetailsPojo=new CallHistoryDetailsPojo();
        callHistoryDetailsPojo.setUserId("121305");
        callHistoryDetailsPojo.setTimestamp("1517502985713");
        callHistoryDetailsPojo.setPhoneNumber("9966710566");
        callHistoryDetailsPojo.setTypeOfCall("I");
        callHistoryDetailsPojo.setDuration("2.20");
        saveCallHistoryDetailsList.add(callHistoryDetailsPojo);
        callHistoryDetails.setCallHistoryDetails(saveCallHistoryDetailsList);

        MyLog.log("calldetails", gson.toJson(callHistoryDetails));
        showProgressDialog();
        mSpyderPresenter.savecallHistoryDetails(callHistoryDetails);
    }
    public  void callGetCallHistoryDetails(){

        UserId userId=new UserId();
        userId.setUserId("121305");

      //  details.setUserDetails(details_);
        MyLog.log("getcalldetails", gson.toJson(userId));
        showProgressDialog();
        mSpyderPresenter.getCallHistoryDetails(userId);

    }
}
