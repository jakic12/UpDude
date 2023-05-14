package com.updude;

import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class NfcModule extends ReactContextBaseJavaModule {
  @ReactMethod
  public String scan() {
    const int timeout = 1000;
    while (MainActivity.lastScanTime - System.currentTimeMillis() < timeout) {
      return MainActivity.lastScanData;
    }
  }
  
  @Override
  public String getName() {
    return "NfcModule";
  }

}