package com.example.musicplayer_app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.musicplayer_app.model.Music;
import com.example.musicplayer_app.repository.MusicRepository;

import java.util.Map;

public class MainViewModel extends AndroidViewModel {


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public Map<Integer, Music> getMusics(){
        return MusicRepository.getMusics(getApplication());
    }
}
