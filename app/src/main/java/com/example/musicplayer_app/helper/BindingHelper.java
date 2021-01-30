package com.example.musicplayer_app.helper;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.musicplayer_app.R;

public class BindingHelper {

    @BindingAdapter("setImage")
    public static void loadImage(ImageView imageView, Uri imagePath){
        Glide.with(imageView.getContext()).
                load(imagePath).
                centerCrop().
                placeholder(R.drawable.ic_null_cover_img).
                into(imageView);
    }
}
