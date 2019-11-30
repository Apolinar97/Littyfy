package com.example.littyfy_v0.Models.Album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("size")
    @Expose
    private String size;


    @SerializedName("#text")
    @Expose
    private String imageUrl;


    public Image(String size, String imageUrl) {
        this.size = size;
        this.imageUrl = imageUrl;
    }

    public Image() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
