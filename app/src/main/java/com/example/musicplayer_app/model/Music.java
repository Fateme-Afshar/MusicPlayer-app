package com.example.musicplayer_app.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.UUID;

public class Music implements Serializable {
        private UUID mId ;
        private String mName;
        private String mPath;
        private Uri mImagePath;
        private String mSingerName;
        private String mDuration;
        private int mAlbumId;
        private boolean mIsPlaying=false;

    public Music(String name, String singerName, String path, int albumId,String duration,Uri imagePath) {
        mId=UUID.randomUUID();
        mName = name;
        mPath = path;
        mSingerName = singerName;
        mAlbumId = albumId;
       mDuration =duration;
       mImagePath=imagePath;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getSingerName() {
        return mSingerName;
    }

    public void setSingerName(String singerName) {
        mSingerName = singerName;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(int albumId) {
        mAlbumId = albumId;
    }

    public String getDuration() {
        return extractMusicDurationToTimeFormat(Integer.parseInt(mDuration)/1000);
    }

    public Uri getImagePath() {
        return mImagePath;
    }

    public void setImagePath(Uri imagePath) {
        mImagePath = imagePath;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String extractMusicDurationToTimeFormat(int musicDuration) {
        int minutes = musicDuration / 60;
        int secondReminder = minutes % 60;
        return (minutes < 10 ? "0" + minutes : minutes) + ":" + (secondReminder < 10 ? "0" + secondReminder : secondReminder);
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setPlaying(boolean playing) {
        mIsPlaying = playing;
    }
}
