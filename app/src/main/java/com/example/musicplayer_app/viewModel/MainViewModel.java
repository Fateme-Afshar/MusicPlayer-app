package com.example.musicplayer_app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.musicplayer_app.model.Music;
import com.example.musicplayer_app.repository.MusicRepository;

import java.util.Map;

public class MainViewModel extends AndroidViewModel {
    private LiveData<Music> mMusicLiveData;

    private Music mMusic;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mMusicLiveData=MusicRepository.mMusicMutableLiveData;
    }

    public Map<Integer, Music> getMusics(){
        return MusicRepository.getMusics(getApplication());
    }

    public void setMusic(Music music) {
        mMusic=music;
       MusicRepository.mMusicMutableLiveData.postValue(music);
    }

    public LiveData<Music> getMusicLiveData() {
        return mMusicLiveData;
    }

    public Music getMusic() {
        return mMusic;
    }
}
