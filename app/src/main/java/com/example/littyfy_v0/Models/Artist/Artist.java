

package com.example.littyfy_v0.Models.Artist;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mbid")
    private String artistID;

    public Artist(String artistName, String artistID) {
        this.name = artistName;
        this.artistID = artistID;
    }

    public Artist() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }
}
