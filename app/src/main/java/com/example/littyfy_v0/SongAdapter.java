package com.example.littyfy_v0;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<SongInfo> SongList;
    private FirebaseAuth maAuth;
    private DatabaseReference usersRef;

    public SongAdapter (List<SongInfo> SongList)
    {
        this.SongList = SongList;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder
    {
        public TextView songTitleText, artistNameText;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            songTitleText = itemView.findViewById(R.id.request_song_name);
            artistNameText = itemView.findViewById(R.id.request_artist_name);
        }
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_requests_display, parent, false);

        maAuth = FirebaseAuth.getInstance();

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongViewHolder holder, int position)
    {
        SongInfo songinfo = SongList.get(position);

        String songTitle = songinfo.getTitle();
        String artistName = songinfo.getArtist();

        holder.songTitleText.setText(songTitle);
        holder.artistNameText.setText(artistName);
    }

    @Override
    public int getItemCount()
    {
        return SongList.size();
    }
}
