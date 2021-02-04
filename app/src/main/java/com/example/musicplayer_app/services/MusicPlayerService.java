package com.example.musicplayer_app.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.musicplayer_app.view.fragment.MainFragment;

import java.io.IOException;

public class MusicPlayerService extends Service {
    private IBinder mBinder=new MusicBinder();
    private MediaPlayer mMediaPlayer =new MediaPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1,CreateNotification.createNotification(this));
        String musicPath=
                intent.getStringExtra(MainFragment.EXTRA_MUSIC_SELECTED);

        if (musicPath==null)
            return START_STICKY;

        setupMediaPlayer(musicPath);

        if (mMediaPlayer.isPlaying())
            pauseMusic();
        else
            playMusic();

        return START_STICKY;
    }

    private void setupMediaPlayer(String musicPath) {
        try {
            mMediaPlayer.setDataSource(musicPath);
            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(0.5f, 0.5f);
            mMediaPlayer.setLooping(false);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void playMusic(){
        if (mMediaPlayer.isPlaying())
            return;
        mMediaPlayer.start();
    }

    public void pauseMusic(){
        if (!mMediaPlayer.isPlaying())
            return;
        mMediaPlayer.pause();
    }

    public class MusicBinder extends Binder {
            public MusicPlayerService getMusicPlayerService(){
                return MusicPlayerService.this;
            }
    }
}
