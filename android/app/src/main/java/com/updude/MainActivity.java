package com.updude;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.Context;

import android.nfc.NfcAdapter;

public class MainActivity extends ReactActivity {
  public static boolean lock = false;
  public static float lastScanTime = 0;
  public static String lastScanData = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    UnlockReceiver unlockRec = new UnlockReceiver();
    this.registerReceiver(unlockRec, new IntentFilter("android.intent.action.USER_PRESENT"));

    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "Default notification channel";
      String description = "For the only persistent notification";
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel channel = new NotificationChannel("default", name, importance);
      channel.setDescription(description);
      // Register the channel with the system; you can't change the importance
      // or other notification behaviors after this
      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }

    super.onCreate(savedInstanceState);
  }

  public static void lock(Context ctx, String notification) {
    MainActivity.lock = true;
    NotifManager.startNotif(ctx, notification);
  }

  public static void unlock(Context ctx) {
    MainActivity.lock = false;
    NotifManager.endNotif(ctx);
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
