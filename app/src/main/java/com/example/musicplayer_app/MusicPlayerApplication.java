package com.example.musicplayer_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.musicplayer_app.services.CreateNotification;

public class MusicPlayerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

     createChannel();
    }

  private void createChannel(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CreateNotification.CHANNEL_ID,
                            CreateNotification.CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
