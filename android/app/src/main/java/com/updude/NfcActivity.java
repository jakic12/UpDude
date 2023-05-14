package com.updude;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.content.IntentFilter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.util.Log;
import android.os.Bundle;

import android.nfc.NfcAdapter;

public class NfcActivity extends ReactActivity {

  private static String toHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (int i = bytes.length - 1; i >= 0; --i) {
      int b = bytes[i] & 0xff;
      if (b < 0x10)
        sb.append('0');
      sb.append(Integer.toHexString(b));
      if (i > 0) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Intent startIntent = getIntent();
    if ((startIntent != null) &&
        (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(startIntent.getAction()) ||
            NfcAdapter.ACTION_TECH_DISCOVERED.equals(startIntent.getAction()))) {

      Tag tag = (Tag) startIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
      assert tag != null;
      String id = toHex(tag.getId());

      Log.d("UpDudeNFC", "NFC intent recieved " + startIntent.getAction() + " // " + id);

      //if(!MainActivity.lock){
      WritableMap params = Arguments.createMap(); // add here the data you want to send
      params.putString("tag_id", id); // <- example

      ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
      ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
      if(reactContext != null) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onNfcTagScanned", params);
      } else {
        reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
          @Override
          public void onReactContextInitialized(ReactContext context) {
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("onNfcTagScanned", params);
            reactInstanceManager.removeReactInstanceEventListener(this);
          }
        });
      }

      //}

      TagStore ts = new TagStore(this);
      if (ts.isTagRegistered(id)) {
        MainActivity.unlock(getApplicationContext());
      }

      MainActivity.lastScanTime = System.currentTimeMillis();
      MainActivity.lastScanData = id;
      super.onCreate(savedInstanceState);
      finish();
    }
  }

  /**
   * Returns the name of the main component registered from JavaScript. This is
   * used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "UpDude";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util
   * class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and
   * Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e.
        // React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
    );
  }
}
