package com.example.musicplayer_app.model;

import android.media.MediaMetadataRetriever;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Music implements Serializable {
        private UUID mId ;
        private String mName;
        private String mPath;
        private String mSingerName;
        private int mDuration;
        private int mAlbumId;
        private boolean mIsPlaying=false;

    public Music(String name, String singerName, String path, int albumId) {
        mId=UUID.randomUUID();
        mName = name;
        mPath = path;
        mSingerName = singerName;
        mAlbumId = albumId;
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

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        mDuration=Integer.parseInt(duration);
    }

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setPlaying(boolean playing) {
        mIsPlaying = playing;
    }
}
