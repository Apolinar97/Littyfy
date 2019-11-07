
package Models;

public class Artist {
    private String artistName;
    private String artistID;
    private String artistImageURL;

    public Artist(String artistName, String artistID, String artistImageURL) {
        this.artistName = artistName;
        this.artistID = artistID;
        this.artistImageURL = artistImageURL;
    }

    public Artist(String artistName, String artistID) {
        this.artistName = artistName;
        this.artistID = artistID;
    }

    public Artist(){}

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public String getArtistImageURL() {
        return artistImageURL;
    }

    public void setArtistImageURL(String artistImageURL) {
        this.artistImageURL = artistImageURL;
    }
}
