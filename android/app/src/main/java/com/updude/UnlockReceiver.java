package com.updude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import android.app.Activity;
import android.os.Bundle;

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager deviceManger = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500); // might need to adjust timeout due to race condition

                    boolean active = deviceManger.isAdminActive(new ComponentName(context, DeviceAdmin.class));
                    if (active && MainActivity.lock) {
                        deviceManger.lockNow();
                    }
                    Log.d("UpDudeUnlock", "Intent recieved " + intent.getAction());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
    }
}
