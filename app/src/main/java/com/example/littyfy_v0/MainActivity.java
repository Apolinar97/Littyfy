package com.example.littyfy_v0;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.littyfy_v0.Models.Track.LastFmTrack;
import com.example.littyfy_v0.Services.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView artistNameTextView = (TextView) findViewById(R.id.artistName);
        artistNameTextView.setText("AYE");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<LastFmTrack> track = api.getTrack("Kanye West", "Follow God", Api.API_KEY);
        track.enqueue(new Callback<LastFmTrack>() {
            @Override
            public void onResponse(Call<LastFmTrack> call, Response<LastFmTrack> response) {
                artistNameTextView.setText(response.body().getTrack().getArtist().getName());
            }

            @Override
            public void onFailure(Call<LastFmTrack> call, Throwable t) {

            }
        });






    }

}
