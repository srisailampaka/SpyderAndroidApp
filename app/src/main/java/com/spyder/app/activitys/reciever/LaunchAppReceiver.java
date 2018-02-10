package com.spyder.app.activitys.reciever;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.spyder.app.activitys.MainActivity;

/**
 * Created by srisailampaka on 10/02/18.
 */

public class LaunchAppReceiver extends BroadcastReceiver {
    String LAUNCHER_NUMBER = "1234567890";
    private static final ComponentName LAUNCHER_COMPONENT_NAME = new ComponentName(
            "com.spyder.app.activitys", "com.spyder.app.activitys.Launcher");

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNubmer = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (LAUNCHER_NUMBER.equals(phoneNubmer)) {
            setResultData(null);

            if (isLauncherIconVisible(context)) {

            } else {
                Intent appIntent = new Intent(context, MainActivity.class);
                appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(appIntent);
            }


        }

    }

    private boolean isLauncherIconVisible(Context context) {
        int enabledSetting = context.getPackageManager().getComponentEnabledSetting(LAUNCHER_COMPONENT_NAME);
        return enabledSetting != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

}
