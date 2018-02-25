package com.spyder.app.activitys.presenter;

import android.content.Context;
import android.widget.Toast;

import com.spyder.app.activitys.request.CallHistoryDetails;
import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserId;
import com.spyder.app.activitys.request.UserPhotoDetailList;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.response.GetCallHistoryResponce;
import com.spyder.app.activitys.response.UserDetailsResponse;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.util.SharedPref;
import com.spyder.app.activitys.webservices.Mediator;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SpyderPresenter implements SpyderContract.Presenter {
    private static final String TAG = SpyderPresenter.class.getSimpleName();
    private SpyderContract.View mView;

    private WeakReference<Context> mContext;
    private Mediator mediator;
    private SharedPref sharedPref;
    public SpyderPresenter(SpyderContract.View view, Context context) {
        mView = view;
        this.mContext = new WeakReference<Context>(context);
        this.mediator = Mediator.getInstance(context);
        sharedPref = new SharedPref(context);
    }
    @Override
    public void saveUserDetails(UserDetails details) {
        Call<UserDetailsResponse> call = mediator.saveUserDetails(details);
        call.enqueue(userCallBack);
    }
    @Override
    public void saveLocationDetails(LocationDetails locationDetails) {
        Call<BaseContext> call = mediator.saveLocationDetails(locationDetails);
        call.enqueue(locationCallBack);
    }
    @Override
    public void savecallHistoryDetails(CallHistoryDetails callHistoryDetails) {
        Call<BaseContext> call = mediator.savecallHistoryDetails(callHistoryDetails);
        call.enqueue(callHistoryCallBack);
    }
    @Override
    public void getCallHistoryDetails(UserId userId) {
        Call<GetCallHistoryResponce> call = mediator.getCallHistoryDetails(userId);
        call.enqueue(getCallHistoryDetailsCallBack);
    }
    @Override
    public void savePhotoDetails(UserPhotoDetailList userPhotoDetailList){
        Call<BaseContext> call=mediator.savePhotoDetails(userPhotoDetailList);
        call.enqueue(savePhotoDetailsCallBack);
    }

    private void handleFailure(Throwable t) {
//        MyLog.log(TAG, "Callback failure: " + t.getMessage());
        mView.failureResponse(t.getMessage());
    }

    private Callback<UserDetailsResponse> userCallBack = new Callback<UserDetailsResponse>() {
        @Override
        public void onResponse(Response<UserDetailsResponse> response) {
            MyLog.log(TAG, response.body().toString() + "");
            if (response.isSuccess()) {

                mView.successResponse(response.body());
                MyLog.log(TAG, response.body().toString() + ""+response.raw());
            } else {
                handleError(response);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            handleFailure(t);
        }
    };
    private Callback<BaseContext> locationCallBack = new Callback<BaseContext>() {
        @Override
        public void onResponse(Response<BaseContext> response) {
            MyLog.log(TAG, response.body().toString() + "");
            MyLog.log("locationCallback Responce",response.body().toString()+"");
            if (response.isSuccess()) {
                MyLog.log("locationCallback Responce Success",response.body().toString()+"");
                mView.successResponse(response.body());
                MyLog.log(TAG, response.body().toString() + ""+response.raw());
                MyLog.log("locationCallback Responce mView",response.body().toString()+"");
            } else {
                handleError(response);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            handleFailure(t);
        }
    };
    private Callback<BaseContext> callHistoryCallBack = new Callback<BaseContext>() {
        @Override
        public void onResponse(Response<BaseContext> response) {
            MyLog.log(TAG, response.body().toString() + "");
            MyLog.log("locationCallback Responce",response.body().toString()+"");
            if (response.isSuccess()) {
                sharedPref.setOneDayTimestamp(System.currentTimeMillis()+"");
                MyLog.log("locationCallback Responce Success",response.body().toString()+"");
                mView.successResponse(response.body());
                MyLog.log(TAG, response.body().toString() + ""+response.raw());
                MyLog.log("locationCallback Responce mView",response.body().toString()+"");
            } else {
                handleError(response);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            handleFailure(t);
        }
    };
    private Callback<GetCallHistoryResponce> getCallHistoryDetailsCallBack = new Callback<GetCallHistoryResponce>() {
        @Override
        public void onResponse(Response<GetCallHistoryResponce> response) {
//            MyLog.log(TAG, response.body().toString() + "");
            if (response.isSuccess()) {

               // mView.successResponse(response.body());
                MyLog.log(TAG, response.body().toString() + ""+response.raw());
            } else {
                handleError(response);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            handleFailure(t);
        }
    };
    private Callback<BaseContext> savePhotoDetailsCallBack = new Callback<BaseContext>() {
        @Override
        public void onResponse(Response<BaseContext> response) {
            MyLog.log(TAG, response.body().toString() + "");
            MyLog.log("photoDetails Responce",response.body().toString()+"");
            if (response.isSuccess()) {
                MyLog.log("photoDetails Responce Success",response.body().toString()+"");
                mView.successResponse(response.body());
                MyLog.log(TAG, response.body().toString() + ""+response.raw());
                MyLog.log("photoDetails Responce mView",response.body().toString()+"");
            } else {
                handleError(response);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            handleFailure(t);
        }
    };
    private void handleError(Response response) {
        String error = "";

        try {
            error = response.errorBody().string();
        } catch (IOException e) {
//            MyLog.log(TAG, e.getMessage());
            e.printStackTrace();
        }
        switch (response.code()) {
            case 503:
                Toast.makeText((Context) mView, "Service Unavailable", Toast.LENGTH_LONG).show();
                break;
            case 505:
                Toast.makeText((Context) mView, error, Toast.LENGTH_LONG).show();
            case 408:
                Toast.makeText((Context) mView, "Time out", Toast.LENGTH_LONG).show();
            default:
                Toast.makeText((Context) mView, "Invalid credential"+" "+error, Toast.LENGTH_LONG).show();
                break;
        }
        mView.failureResponse(error);
    }



}
