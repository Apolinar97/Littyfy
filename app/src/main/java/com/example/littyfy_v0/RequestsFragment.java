package com.example.littyfy_v0;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private RecyclerView RequestList;

    private DatabaseReference songReqRef;

    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View requestFragmentView = inflater.inflate(R.layout.fragment_requests, container, false);


        RoomActivity roomActivity = (RoomActivity)getActivity();
        String roomName = roomActivity.roomName;

        RequestList = requestFragmentView.findViewById(R.id.song_requests_list);
        RequestList.setLayoutManager(new LinearLayoutManager(getContext()));

        songReqRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomName).child("Requests");

        return requestFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<SongInfo> options =
                new FirebaseRecyclerOptions.Builder<SongInfo>()
                        .setQuery(songReqRef, SongInfo.class)
                        .build();

        FirebaseRecyclerAdapter<SongInfo, RequestsViewHolder> adapter =
                new FirebaseRecyclerAdapter<SongInfo, RequestsViewHolder>(options)
                {
                    @NonNull
                    @Override
                    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_requests_display, parent, false);
                        return new RequestsViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull RequestsViewHolder requestsViewHolder, final int i, @NonNull SongInfo songInfo)
                    {
                        final DatabaseReference itemRef = songReqRef.child(getRef(i).getKey());

                        requestsViewHolder.songTitleText.setText(songInfo.getTitle());
                        requestsViewHolder.artistNameText.setText(songInfo.getArtist());

                        requestsViewHolder.deny.setOnClickListener(new View.OnClickListener() {
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
        public AppCompatButton accept, deny;

        public RequestsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            songTitleText = itemView.findViewById(R.id.request_song_name);
            artistNameText = itemView.findViewById(R.id.request_artist_name);
            accept = itemView.findViewById(R.id.request_accept_button);
            deny = itemView.findViewById(R.id.request_decline_button);
        }
    }
}
