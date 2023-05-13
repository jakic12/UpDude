package com.updude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;



public class UnlockReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager deviceManger = (DevicePolicyManager) context.getSystemService(
        Context.DEVICE_POLICY_SERVICE);
        
        boolean active = deviceManger.isAdminActive(new ComponentName(context, DeviceAdmin.class));
        if (active) {
            deviceManger.lockNow();
        }
        // log intent
        Log.d("UpDudeUnlock", "Intent recieved "+intent.getAction());
    }

}