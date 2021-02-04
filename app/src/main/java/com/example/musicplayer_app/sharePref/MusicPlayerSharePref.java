package com.example.musicplayer_app.sharePref;

import android.content.Context;
import android.content.SharedPreferences;

public class MusicPlayerSharePref {

    public static SharedPreferences getSharePreferences(Context context){
        return context.getSharedPreferences
                ("com.example.musicplayer_app",
                        Context.MODE_PRIVATE);
    }
}
