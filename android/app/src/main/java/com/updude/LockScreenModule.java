package com.updude;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

import android.util.Log;

public class LockScreenModule extends ReactContextBaseJavaModule {
  LockScreenModule(ReactApplicationContext context) {
    super(context);
  }

  @Override
  public String getName() {
    return "LockScreenModule";
  }

  @ReactMethod
  public void lockScreen() {
    Log.d("LockScreenModule", "Lock screen called");
  }
}