package com.updude;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;
import android.content.IntentFilter;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.Context;


import android.nfc.NfcAdapter;

public class MainActivity extends ReactActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Intent startIntent = getIntent();
    Log.d("UpDudeNFC", "NNNNNNNNNN intent "+startIntent.getAction());
    if ((startIntent != null) &&
        (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(startIntent.getAction()) ||
        NfcAdapter.ACTION_TECH_DISCOVERED.equals(startIntent.getAction()))) {
        Log.d("UpDudeNFC", "NFC intent recieved "+startIntent.getAction());
    }
      UnlockReceiver unlockRec = new UnlockReceiver();
      this.registerReceiver(unlockRec, new IntentFilter("android.intent.action.USER_PRESENT"));

      // create dummy contex
      Context context = getApplicationContext();
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
      sharedPreferences.edit().putBoolean("lock", false).apply();
      super.onCreate(savedInstanceState);
  }


  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "UpDude";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }
}
