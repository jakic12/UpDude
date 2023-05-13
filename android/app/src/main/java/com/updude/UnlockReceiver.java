package com.updude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

// i need getApplicationContext
import android.app.Activity;
import android.os.Bundle;

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager deviceManger = (DevicePolicyManager) context.getSystemService(
                Context.DEVICE_POLICY_SERVICE);

        // wait for 5 seconds
        try {
            Thread.sleep(0);
            // get application context after 5 seconds
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            Boolean lock = sharedPreferences.getBoolean("lock", false);

            boolean active = deviceManger.isAdminActive(new ComponentName(context, DeviceAdmin.class));
            if (active && lock) {
                deviceManger.lockNow();
            }
            // log intent
            Log.d("UpDudeUnlock", "Intent recieved " + intent.getAction());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}