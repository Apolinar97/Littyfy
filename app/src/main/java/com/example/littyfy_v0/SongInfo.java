package com.example.littyfy_v0;

public class SongInfo {

    private String title, artist, type;

    private SongInfo () {}


    public SongInfo(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setFrom(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
