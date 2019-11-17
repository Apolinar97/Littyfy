package com.example.littyfy_v0;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private View requestFragmentView;
    private ListView requestList;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_requests = new ArrayList<>();


    private FirebaseAuth mAuth;
    private DatabaseReference SongReqRef;

    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestFragmentView = inflater.inflate(R.layout.fragment_requests, container, false);

        RoomActivity roomActivity = (RoomActivity)getActivity();

        mAuth = FirebaseAuth.getInstance();
        SongReqRef = FirebaseDatabase.getInstance().getReference().child("Rooms").child(roomActivity.roomName).child("Requests");

        requestList = requestFragmentView.findViewById(R.id.request_list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, list_of_requests);
        requestList.setAdapter(arrayAdapter);

        RetrieveRequests();

        return requestFragmentView;
    }

    private void RetrieveRequests()
    {
        SongReqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                Iterator it = dataSnapshot.getChildren().iterator();

                while(it.hasNext())
                {
                    set.add(((DataSnapshot)it.next()).getKey());
                }

                list_of_requests.clear();
                list_of_requests.addAll(set);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
