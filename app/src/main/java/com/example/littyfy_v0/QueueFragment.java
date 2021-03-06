package com.example.littyfy_v0;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {


    private RecyclerView RequestList;

    private FirebaseAuth mAuth;
    private DatabaseReference songReqRef;

    private String currentUserID;
    private String dj;


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
        dj = roomActivity.dj;

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        RequestList = requestFragmentView.findViewById(R.id.song_queue_list);
        RequestList.setLayoutManager(new LinearLayoutManager(getContext()));
        SeparatorDecoration decoration = new SeparatorDecoration(getContext(), Color.GRAY, 1.5f);
        RequestList.addItemDecoration(decoration);

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
                        //Log.d("URL TEST", songInfo.getUrl());

                        Picasso.get().load(songInfo.getUrl()).into(requestsViewHolder.imgViewTrack);


                        if (!currentUserID.equals(dj))
                        {
                            requestsViewHolder.remove.setVisibility(View.INVISIBLE);
                        } else {
                            requestsViewHolder.remove.setVisibility(View.VISIBLE);
                        }




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
        public ImageView imgViewTrack;

        public RequestsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            remove = itemView.findViewById(R.id.queue_delete_button);
            songTitleText = itemView.findViewById(R.id.queue_song_name);
            artistNameText = itemView.findViewById(R.id.queue_artist_name);
            imgViewTrack = itemView.findViewById(R.id.imgview_track);
        }
    }
}

