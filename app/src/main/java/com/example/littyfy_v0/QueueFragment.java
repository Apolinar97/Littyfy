package com.example.littyfy_v0;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Queue;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {


    private RecyclerView RequestList;

    private DatabaseReference songReqRef;



    public QueueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View requestFragmentView = inflater.inflate(R.layout.fragment_queue, container, false);


        RoomActivity roomActivity = (RoomActivity)getActivity();
        String roomName = roomActivity.roomName;

        RequestList = requestFragmentView.findViewById(R.id.song_queue_list);
        RequestList.setLayoutManager(new LinearLayoutManager(getContext()));

        songReqRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomName);

        return requestFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<SongInfo> options =
                new FirebaseRecyclerOptions.Builder<SongInfo>()
                        .setQuery(songReqRef.child("Queue"), SongInfo.class)
                        .build();

        FirebaseRecyclerAdapter<SongInfo, QueueFragment.RequestsViewHolder> adapter =
                new FirebaseRecyclerAdapter<SongInfo, QueueFragment.RequestsViewHolder>(options)
                {
                    @NonNull
                    @Override
                    public QueueFragment.RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_queue_display, parent, false);
                        return new QueueFragment.RequestsViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull QueueFragment.RequestsViewHolder requestsViewHolder, final int i, @NonNull final SongInfo songInfo)
                    {
                        final DatabaseReference itemRef = songReqRef.child("Queue").child(getRef(i).getKey());

                        requestsViewHolder.songTitleText.setText(songInfo.getTitle());
                        requestsViewHolder.artistNameText.setText(songInfo.getArtist());

                        requestsViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                itemRef.setValue(null);
                            }
                        });
                    }
                };



        RequestList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class RequestsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView songTitleText, artistNameText;
        public ImageButton remove;

        public RequestsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            remove = itemView.findViewById(R.id.queue_delete_button);
            songTitleText = itemView.findViewById(R.id.queue_song_name);
            artistNameText = itemView.findViewById(R.id.queue_artist_name);
        }
    }
}

