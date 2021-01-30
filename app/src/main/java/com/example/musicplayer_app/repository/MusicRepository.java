package com.example.musicplayer_app.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer_app.model.Music;

import java.util.HashMap;
import java.util.Map;

public class MusicRepository {

    public static Map<Integer,Music> getMusics(Context context){
        Map<Integer,Music> musicMap=new HashMap<>();
        int counter=0;

        Context leekSafeContext=context.getApplicationContext();
        Uri musicExternalUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=
                leekSafeContext.getContentResolver().
                        query(musicExternalUri,
                                null,
                                null,
                                null,
                                null);

        if (cursor==null || cursor.getCount()==0)
            return null;
        try {

            while (cursor.moveToNext()){
                 musicMap.put(counter,extractMusicFromCursor(cursor));
                 counter++;
            }

        }finally {
            cursor.close();
        }

        return musicMap;
    }

    private static Music extractMusicFromCursor(Cursor cursor) {
        String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        String singerName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String path=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        int albumId=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

        return new Music(name,singerName,path,albumId);
    }
}
