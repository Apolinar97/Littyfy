package com.example.littyfy_v0;

public class SongInfo {

    private String title, artist;
    private String url;

    public SongInfo () {}


    public SongInfo(String title, String artist, String trackImageURL) {
        this.title = title;
        this.artist = artist;
        this.url = trackImageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getTrackImageURL() {return url;}

    public void setFrom(String title) {
        this.title = title;
    }

    public void setTrackImageURL(String url) {this.url = url;}

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
