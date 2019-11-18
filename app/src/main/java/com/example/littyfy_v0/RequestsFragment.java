package com.example.littyfy_v0;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private View requestFragmentView;
    private RecyclerView RequestList;

    private final List<SongInfo> songList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private SongAdapter songAdapter;

    private DatabaseReference songReqRef;

    private String songName, artistName, roomName;

    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestFragmentView = inflater.inflate(R.layout.fragment_requests, container, false);


        RoomActivity roomActivity = (RoomActivity)getActivity();
        roomName = roomActivity.roomName;

        songAdapter = new SongAdapter(songList);
        RequestList = requestFragmentView.findViewById(R.id.song_requests_list);
        linearLayoutManager = new LinearLayoutManager(getContext());
        RequestList.setLayoutManager(linearLayoutManager);
        RequestList.setAdapter(songAdapter);

        songReqRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomName).child("Requests");

        RequestList.setLayoutManager(new LinearLayoutManager(getContext()));

        return requestFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        songReqRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SongInfo songinfo = dataSnapshot.getValue(SongInfo.class);

                songList.add(songinfo);

                songAdapter.notifyDataSetChanged();

                RequestList.smoothScrollToPosition(RequestList.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
