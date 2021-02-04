package com.example.musicplayer_app.services;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.musicplayer_app.R;
import com.example.musicplayer_app.model.Music;

public class CreateNotification {
    public static final String CHANNEL_NAME = "musicPlayer";
    public static final String CHANNEL_ID = "musicPlayerChannel";

    public static Notification createNotification(Context context){

        return new NotificationCompat.
                Builder(context, CHANNEL_ID).
                setSmallIcon(R.drawable.ic_luncher_round).
                setOnlyAlertOnce(true).
                setShowWhen(false).
                setPriority(NotificationCompat.PRIORITY_LOW).
                build();
    }
}
