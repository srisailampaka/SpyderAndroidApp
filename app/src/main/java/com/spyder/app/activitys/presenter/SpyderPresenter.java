package com.spyder.app.activitys.presenter;

import android.content.Context;
import android.widget.Toast;

import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.webservices.Mediator;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SpyderPresenter implements SpyderContract.Presenter {
    private static final String TAG = SpyderPresenter.class.getSimpleName();
    private SpyderContract.View mView;

    private WeakReference<Context> mContext;
    private Mediator mediator;
    public SpyderPresenter(SpyderContract.View view, Context context) {
        mView = view;
        this.mContext = new WeakReference<Context>(context);
        this.mediator = Mediator.getInstance(context);
    }
    @Override
    public void saveUserDetails(UserDetails details) {
        Call<BaseContext> call = mediator.saveUserDetails(details);
        call.enqueue(loginCallBack);
    }

    private void handleFailure(Throwable t) {
//        MyLog.log(TAG, "Callback failure: " + t.getMessage());
        mView.failureResponse(t.getMessage());
    }

    private Callback<BaseContext> loginCallBack = new Callback<BaseContext>() {
        @Override
        public void onResponse(Response<BaseContext> response) {
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
