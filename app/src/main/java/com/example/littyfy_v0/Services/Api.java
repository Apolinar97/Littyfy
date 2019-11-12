
package com.example.littyfy_v0.Services;
import com.example.littyfy_v0.Models.Artist.LastFmArtist;
import com.example.littyfy_v0.Models.Track.LastFmTrack;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface Api {
    String API_KEY = "b4639fe6967122314286982ff3fcb219";

    @GET("?method=artist.getinfo&format=json")
    Call<LastFmArtist> getArtistByName(@Query("artist") String name,
                                       @Query("api_key") String apiKey);

    @GET("?method=track.getinfo&format=json")
    Call<LastFmTrack> getTrack(@Query("artist") String artistName,
                               @Query("track") String trackName,
                               @Query("api_key") String apiKey);

}
