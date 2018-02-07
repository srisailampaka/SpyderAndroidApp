package com.spyder.app.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spyder.app.R;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.UserDetails;
import com.spyder.app.activitys.request.UserDetails_;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.MyLog;
import com.spyder.app.activitys.view.activities.BaseActivity;
import com.spyder.app.activitys.webservices.Mediator;

public class MainActivity extends BaseActivity implements SpyderContract.View {

    private Mediator mediator;
    protected SpyderPresenter mSpyderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediator = Mediator.getInstance(getApplicationContext());
        UserDetails details = new UserDetails();
        UserDetails_ details_ = new UserDetails_();
        mSpyderPresenter = new SpyderPresenter(this, getApplicationContext());
        Gson gson = new Gson();
        details_.setDeviceid("1517502985713");
        details_.setUserId("1");
        details_.setUsername("121305");

        details.setUserDetails(details_);
        MyLog.log("details", gson.toJson(details));
        showProgressDialog();
        mSpyderPresenter.saveUserDetails(details);

    }

    @Override
    public void successResponse(BaseContext baseContext) {
        hideProgressDialog();
        MyLog.log("success", "cccccccccccc" + baseContext.getStatus());

    }

    @Override
    public void failureResponse(String error) {
        hideProgressDialog();
        MyLog.log("failure", "cccccccccccc");

    }
}
