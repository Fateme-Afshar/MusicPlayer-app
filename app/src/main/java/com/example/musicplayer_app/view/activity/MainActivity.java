package com.example.musicplayer_app.view.activity;

import androidx.fragment.app.Fragment;

import com.example.musicplayer_app.view.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }
}