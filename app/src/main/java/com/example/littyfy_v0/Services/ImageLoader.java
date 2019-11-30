package com.example.littyfy_v0.Services;

import android.widget.ImageView;

import com.example.littyfy_v0.Models.Album.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageLoader {
//TODO: Add place holder, error images
    public void  loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    public String getSmallImage(List<Image> images) {
        for (Image trackImage : images) {
            if(trackImage.getSize() == "small") {
                return trackImage.getImageUrl();
            }
        }
        return images.get(1).getImageUrl();

    }

}
