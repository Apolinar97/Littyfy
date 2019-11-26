package com.example.littyfy_v0.Models.Album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {
    @SerializedName("artist")
    @Expose
    private String artistName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image")
    @Expose
    private List<Image> images = null;


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
