
package Models;

import java.util.ArrayList;

public class Album {
    private String albumName;
    private String mID;
    private String albumType;
    private ArrayList<Track> albumTracks;
    private Artist artist;


    public Album(String albumName, String mID, String albumType, ArrayList<Track> albumTracks, Artist artist) {
        this.albumName = albumName;
        this.mID = mID;
        this.albumType = albumType;
        this.albumTracks = albumTracks;
        this.artist = artist;
    }

    public Album(String albumName, String mID, String albumType, Artist artist) {
        this.albumName = albumName;
        this.mID = mID;
        this.albumType = albumType;
        this.artist = artist;
    }

    public Album(){}


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public ArrayList<Track> getAlbumTracks() {
        return albumTracks;
    }

    public void setAlbumTracks(ArrayList<Track> albumTracks) {
        this.albumTracks = albumTracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
