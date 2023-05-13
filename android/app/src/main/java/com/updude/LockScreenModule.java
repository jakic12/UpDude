package com.updude;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;

// import startActivityForResult
import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import com.facebook.react.bridge.Callback;

public class LockScreenModule extends ReactContextBaseJavaModule {
  DevicePolicyManager deviceManger;
  ActivityManager activityManager;
  ComponentName compName;
  ReactApplicationContext ctx;
  public static final int RESULT_ENABLE = 11;

  LockScreenModule(ReactApplicationContext context) {
    super(context);

    deviceManger = (DevicePolicyManager) context.getSystemService(
        Context.DEVICE_POLICY_SERVICE);
    activityManager = (ActivityManager) context.getSystemService(
        Context.ACTIVITY_SERVICE);

    compName = new ComponentName(context, DeviceAdmin.class);
    ctx = context;
  }

  @ReactMethod
  public void enableAdmin() {
    Log.d("LockScreenModule", "enable admin called");
    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "You should enable the app!");
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    getReactApplicationContext().startActivityForResult(intent, RESULT_ENABLE, Bundle.EMPTY);
    // ctx.startActivity(intent);
  }

  @Override
  public String getName() {
    return "LockScreenModule";
  }

  @ReactMethod
  public void isAdminActive(Callback cb) {
    boolean active = deviceManger.isAdminActive(compName);
    cb.invoke(active);
  }

  @ReactMethod
  public void lockScreen() {
    Log.d("LockScreenModule", "Lock screen called");
    boolean active = deviceManger.isAdminActive(compName);
    if (active) {
      deviceManger.lockNow();
    }
  }
}