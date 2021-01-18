package com.example.musicplayer_app.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.musicplayer_app.view.fragment.MainFragment;

import java.io.IOException;

public class MainActivity extends SingleFragmentActivity {
    MyService mMyService;
    boolean isBound=false;

    private ServiceConnection mServiceConnection=new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.BinderClass binder = (MyService.BinderClass) service;
            mMyService = binder.getService();

            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound=false;
        }
    };

    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    Intent intent=new Intent(this,MyService.class);
    bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

}