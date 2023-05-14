package com.updude;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class NfcModule extends ReactContextBaseJavaModule {
  @ReactMethod
  public void scan(Callback cb) {
    final int timeout = 1000;
    while (MainActivity.lastScanTime - System.currentTimeMillis() >= timeout) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    cb.invoke(MainActivity.lastScanData);
  }

  @Override
  public String getName() {
    return "NfcModule";
  }

}