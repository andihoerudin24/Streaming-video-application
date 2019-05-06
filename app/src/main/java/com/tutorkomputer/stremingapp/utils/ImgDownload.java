package com.tutorkomputer.stremingapp.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tutorkomputer.stremingapp.R;

public class ImgDownload {

    public static void picasso(String URL, ImageView imageView){

        Picasso.get().load(URL).placeholder(R.drawable.no_image).error(R.drawable.no_preview).into(imageView);
    }
}
