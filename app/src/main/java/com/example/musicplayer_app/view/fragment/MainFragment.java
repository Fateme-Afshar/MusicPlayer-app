package com.example.musicplayer_app.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer_app.R;
import com.example.musicplayer_app.databinding.FragmentPagerBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private static final String PERMISSION=
            Manifest.permission.READ_EXTERNAL_STORAGE;
    private FragmentPagerBinding mBinding;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        if (checkHasPermission()){
            //TODO: GET AND SHOW MUSICS
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
                    //TODO: GET AND SHOW MUSICS
                }
        }
    }
}