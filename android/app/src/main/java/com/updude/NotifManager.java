package com.updude;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class NotifManager {
  static void startNotif(Context context, String description) {
    Intent i = new Intent(context, NotificationService.class);
    i.putExtra("description", description);
    context.startService(i);
  }

  static void endNotif(Context context) {
    // end the foreground service
    Intent i = new Intent(context, NotificationService.class);
    context.stopService(i);
    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    nm.cancel(1337);
  }
}
