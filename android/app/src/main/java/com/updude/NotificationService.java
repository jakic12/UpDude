package com.updude;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {
  @Override
  public void onCreate() {
    super.onCreate();
    Log.d("UpDude", "onCreate");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d("UpDude", "Service started");
    String value = intent.getStringExtra("description");
    this.startForeground(value);
    return Service.START_STICKY;
  }

  private void startForeground(String desc) {
    startForeground(1337, getMyActivityNotification(desc));
  }

  private Notification getMyActivityNotification(String text) {
    Notification notification = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      notification = new Notification.Builder(this, "default")
          .setOngoing(true)
          .setContentTitle("Your phone is locked by updude")
          .setContentText(text)
          .setSmallIcon(R.drawable.main_icon_foreground)
          .build();
    }

    return notification;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}