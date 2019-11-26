package com.example.littyfy_v0.Services;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {
//TODO: Add place holder, error images
    private void  LoadImage(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

}
