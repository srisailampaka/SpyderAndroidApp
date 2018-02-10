package com.spyder.app.activitys.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.spyder.app.activitys.util.CommonUtil.isNetworkAvailable;

/**
 * Created by srisailampaka on 10/02/18.
 */

public class NetworkChangedReceiver  extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {

        if (isNetworkAvailable(context))
        {
            Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();


        }
    }
}
