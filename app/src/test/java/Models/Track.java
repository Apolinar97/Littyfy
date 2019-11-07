package Models;

public class Track {

    private String trackName;
    private Integer trackID;
    private String trackType;
    private String trackImageURL;

    private String artistName; //TODO: change to artist object, maybe.
    private String albumName; //TODO: Change to album object, maybe.


    public Track(String trackName, Integer trackID, String trackType, String trackImageURL, String artistName, String albumName) {
        this.trackName = trackName;
        this.trackID = trackID;
        this.trackType = trackType;
        this.trackImageURL = trackImageURL;
        this.artistName = artistName;
        this.albumName = albumName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackID() {
        return trackID;
    }

    public void setTrackID(Integer trackID) {
        this.trackID = trackID;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getTrackImageURL() {
        return trackImageURL;
    }

    public void setTrackImageURL(String trackImageURL) {
        this.trackImageURL = trackImageURL;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
