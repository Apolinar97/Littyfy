
package com.example.littyfy_v0.Models.Track;

import com.example.littyfy_v0.Models.Album.Album;
import com.example.littyfy_v0.Models.Artist.Artist;
import com.example.littyfy_v0.Models.Artist.LastFmArtist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("url")
    @Expose
    private String url;


    @SerializedName("artist")
    @Expose
    Artist artist;

    @SerializedName("album")
    @Expose
    private Album album;


    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
