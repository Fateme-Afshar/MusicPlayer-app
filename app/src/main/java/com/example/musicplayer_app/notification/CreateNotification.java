package com.example.musicplayer_app.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationManagerCompat;

import com.example.musicplayer_app.R;
import com.example.musicplayer_app.broadCastReciver.NotificationActionBroadCast;
import com.example.musicplayer_app.model.Music;
import com.example.musicplayer_app.utils.PhotoUtils;

public class CreateNotification {
    public static final String CHANNEL_NAME = "Music Player Channel";
    public static final String CHANNEL_ID = "musicChannel";

    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_NEXT = "action_next";

    public static final String TAG = "NotificationMusicPlayer";
    private static final int NOTIFICATION_ID = 1;

    public static Notification sNotification;

    public static void createNotification(Context context, int playBtn, int pos, int size, Music music){
        Context leekSafeContext = context.getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat notificationManagerCompat=
                    NotificationManagerCompat.from(context.getApplicationContext());
            MediaSession mediaSession=new MediaSession(context.getApplicationContext(), TAG);

            PendingIntent pendingIntentPrev;
            int drw_prev;
            if (pos == 0) {
                pendingIntentPrev = null;
                drw_prev = 0;
            } else {
                Intent intentPrev = new Intent(leekSafeContext,
                        NotificationActionBroadCast.class).
                        setAction(ACTION_PREVIOUS);

                pendingIntentPrev = PendingIntent.getBroadcast(leekSafeContext,
                        0,
                        intentPrev,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                drw_prev = R.drawable.ic_prev;
            }

            Intent intentPlay = new Intent(leekSafeContext,
                    NotificationActionBroadCast.class).
                    setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(leekSafeContext,
                    0,
                    intentPlay,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
            if (pos == 0) {
                pendingIntentNext = null;
                drw_next = 0;
            } else {
                Intent intentNext = new Intent(context.getApplicationContext(),
                        NotificationActionBroadCast.class).
                        setAction(ACTION_NEXT);

                pendingIntentNext = PendingIntent.getBroadcast(context.getApplicationContext(),
                        0,
                        intentNext,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                drw_next = R.drawable.ic_next;
            }

            Bitmap icon = PhotoUtils.getScalePhoto(music.getPath(), 120, 120);

            sNotification = new androidx.core.app.NotificationCompat.
                    Builder(context.getApplicationContext(), CHANNEL_ID).
                    setSmallIcon(R.drawable.ic_luncher_dark).
                    setContentText(music.getName()).
                    setContentText(music.getSingerName()).
                    setLargeIcon(icon).
                    setOnlyAlertOnce(true).
                    setShowWhen(false).
                    addAction(drw_prev, "Previous", pendingIntentPrev).
                    addAction(playBtn, "Play", pendingIntentPlay).
                    addAction(drw_prev, "Next", pendingIntentNext).
                    setStyle(new androidx.media.app.NotificationCompat.MediaStyle().
                            setShowActionsInCompactView(0, 1, 2).
                            setMediaSession(MediaSessionCompat.Token.fromToken(mediaSession.getSessionToken()))).
                    setPriority(androidx.core.app.NotificationCompat.PRIORITY_LOW).
                    build();

            notificationManagerCompat.notify(NOTIFICATION_ID, sNotification);
        }

        }
    }
