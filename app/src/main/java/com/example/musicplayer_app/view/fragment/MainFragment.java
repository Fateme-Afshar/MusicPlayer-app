package com.example.musicplayer_app.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musicplayer_app.R;
import com.example.musicplayer_app.adapter.MusicAdapter;
import com.example.musicplayer_app.databinding.FragmentPagerBinding;
import com.example.musicplayer_app.model.Music;
import com.example.musicplayer_app.services.MusicPlayerService;
import com.example.musicplayer_app.utils.ProgramUtils;
import com.example.musicplayer_app.viewModel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment{
    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private static final String PERMISSION=
            Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String EXTRA_MUSIC_SELECTED =
            "com.example.musicplayer_app.view.fragment.MusicSelected";
    private FragmentPagerBinding mBinding;

    private MainViewModel mViewModel;

    private MusicPlayerService mMusicPlayerService;
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayerService.MusicBinder musicBinder=
                    (MusicPlayerService.MusicBinder) service;

            mMusicPlayerService=musicBinder.getMusicPlayerService();
            mBound =true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private boolean mBound;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();

        Intent intent=new Intent(getActivity(),MusicPlayerService.class);
        getActivity().startService(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent=new Intent(getActivity(),MusicPlayerService.class);
        getActivity().bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_pager,
                        container,
                        false);

        return mBinding.getRoot();
    }


    private void setupAdapter() {
        MusicAdapter adapter=new MusicAdapter(getActivity(),mViewModel.getMusics());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(adapter);

        adapter.setCallback(new MusicAdapter.MusicAdapterCallback() {
            @Override
            public void sendMusicInfo(Music music, int position) {
                mViewModel.setMusic(music);
            }
        });
    }

    private void setupViewModel(){
        mViewModel=new ViewModelProvider(this).get(MainViewModel.class);

        mViewModel.getMusicLiveData().observe(getActivity(), new Observer<Music>() {
            @Override
            public void onChanged(Music music) {
                Intent intent=new Intent(getActivity(),MusicPlayerService.class);
                intent.putExtra(EXTRA_MUSIC_SELECTED,music.getPath());
                getActivity().startService(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        if (checkHasPermission()){
            setupAdapter();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkHasPermission(){
        if (getActivity().checkSelfPermission(PERMISSION)
                ==PackageManager.PERMISSION_GRANTED){
            return true;
        }else if (getActivity().shouldShowRequestPermissionRationale(PERMISSION)){
            showDialogView();
        }else {
            requestPermissions(new String[]{PERMISSION}, REQUEST_CODE_EXTERNAL_STORAGE);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showDialogView(){
        AlertDialog alertDialog=new AlertDialog.Builder(getContext()).
                setView(R.layout.layout_dialog_discripton_permission).
                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{PERMISSION}, REQUEST_CODE_EXTERNAL_STORAGE);
            }
        }).setNegativeButton(android.R.string.cancel, null).
                create();

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setupAdapter();
                }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(mConnection);
    }
}