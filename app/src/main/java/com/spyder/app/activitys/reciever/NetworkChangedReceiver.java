package com.spyder.app.activitys.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;
import com.spyder.app.activitys.request.LocationDetail;
import com.spyder.app.activitys.request.LocationDetails;
import com.spyder.app.activitys.response.BaseContext;
import com.spyder.app.activitys.util.GetDetailsInformation;
import com.spyder.app.activitys.util.SharedPref;
import com.spyder.app.activitys.view.activities.LocationDatabase;

import java.util.List;

import static com.spyder.app.activitys.util.CommonUtil.isNetworkAvailable;

/**
 * Created by srisailampaka on 10/02/18.
 */

public class NetworkChangedReceiver  extends BroadcastReceiver implements SpyderContract.View
{
    private SpyderPresenter mSpyderPresenter;
    private LocationDatabase db;
    private static SharedPref sharedPref;
    @Override
    public void onReceive(Context context, Intent intent) {
        mSpyderPresenter = new SpyderPresenter(this, context);
        sharedPref = new SharedPref(context);
        if (isNetworkAvailable(context))
        {
            Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();

          callAPIForDBLocationDetails(context);
            if(!sharedPref.getLastImageTimestamp().equalsIgnoreCase("0"))
            {GetDetailsInformation getDetailsInformation=new GetDetailsInformation(context);
            getDetailsInformation.getTheAllPhotos();
        }
    }}

    private void callAPIForDBLocationDetails(Context context)
    {
         db = new LocationDatabase(context);
        List<LocationDetail> locationdetail = db.getALLLocationData();
        LocationDetails locationDetails=new LocationDetails();
        locationDetails.setLocationDetails(locationdetail);
        mSpyderPresenter.saveLocationDetails(locationDetails);

    }

    @Override
    public void successResponse(BaseContext baseContext) {
        db.deleteTheLocationDetails();

    }

    @Override
    public void failureResponse(String error) {

    }
}
