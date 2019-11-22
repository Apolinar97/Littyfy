package com.example.littyfy_v0;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference RoomRef;
    private String currentUserID;

    private RecyclerView roomList;

    String status = "default", picture = "default";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        RootRef = FirebaseDatabase.getInstance().getReference();
        RoomRef = FirebaseDatabase.getInstance().getReference().child("Rooms");

        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Littyfy Rooms");

        roomList = findViewById(R.id.room_recycler_view);
        roomList.setLayoutManager(new LinearLayoutManager(this));
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        roomList.addItemDecoration(decoration);
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            SendUserToLoginActivity();
        }
        else
        {
            VerifyUserExists();
        }
    }

    private void VerifyUserExists() {
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("status")) {
                        status = dataSnapshot.child("status").getValue().toString();
                    }
                    if (dataSnapshot.hasChild("image")) {
                        picture = dataSnapshot.child("image").getValue().toString();
                    }
                    if (dataSnapshot.child("name").exists()) {
                        RetrieveRooms();
                    } else {
                        SendUserToSettingsActivity();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void RetrieveRooms()
    {
        FirebaseRecyclerOptions<RoomModel> options =
                new FirebaseRecyclerOptions.Builder<RoomModel>()
                        .setQuery(RoomRef, RoomModel.class)
                        .build();

        FirebaseRecyclerAdapter<RoomModel, RoomViewHolder> adapter =
                new FirebaseRecyclerAdapter<RoomModel, RoomViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i, @NonNull final RoomModel roomModel) {
                        roomViewHolder.roomViewName.setText(roomModel.getName());
                        roomViewHolder.roomStatus.setText(roomModel.getStatus());
                        Picasso.get().load(roomModel.getPicture()).placeholder(R.drawable.profile_image).into(roomViewHolder.roomImage);
/*
                        if (currentUserID != roomModel.getDj())
                        {
                            roomViewHolder.roomDeleteButton.setVisibility(View.INVISIBLE);
                        }
*/
                        final DatabaseReference itemRef = RootRef.child("Rooms").child(getRef(i).getKey());

                        roomViewHolder.roomDeleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                itemRef.setValue(null);
                            }
                        });


                        roomViewHolder.roomLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String currentRoomName = roomModel.getName();
                                Intent roomActivity = new Intent(MainActivity.this, RoomActivity.class);
                                roomActivity.putExtra("roomName", currentRoomName);
                                roomActivity.putExtra("dj", roomModel.getName());
                                startActivity(roomActivity);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_display, parent, false);
                        return new RoomViewHolder(view);
                    }
                };

        roomList.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            mAuth.signOut();
            SendUserToLoginActivity();
        }

        if (item.getItemId() == R.id.main_settings_option)
        {
            SendUserToSettingsActivity();
        }

        if (item.getItemId() == R.id.main_create_room_option)
        {
            RequestNewRoom();
        }

        return true;
    }


    private void RequestNewRoom()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Room Name");

        final EditText roomNameField = new EditText(MainActivity.this);
        roomNameField.setHint("Awesome Party Room");
        builder.setView(roomNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String roomName = roomNameField.getText().toString();

                if (TextUtils.isEmpty(roomName))
                {
                    Toast.makeText(MainActivity.this, "Please enter room name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CreateNewRoom(roomName);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewRoom(final String roomName)
    {
        RootRef.child("Rooms").child(roomName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, roomName + " was created successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        currentUserID = mAuth.getCurrentUser().getUid();



        HashMap<String, Object> djInfoMap = new HashMap<>();
        djInfoMap.put("dj", currentUserID);
        djInfoMap.put("name", roomName);
        djInfoMap.put("status", status);
        djInfoMap.put("picture", picture);

        RootRef.child("Rooms").child(roomName).updateChildren(djInfoMap);
    }




    private void SendUserToSettingsActivity()
    {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


    public static class RoomViewHolder extends RecyclerView.ViewHolder
    {
        public TextView roomViewName, roomStatus;
        public ImageView roomImage;
        public LinearLayout roomLinearLayout;
        public ImageButton roomDeleteButton;

        public RoomViewHolder(@NonNull View itemView)
        {
            super(itemView);

            roomViewName = itemView.findViewById(R.id.room_name);
            roomStatus = itemView.findViewById(R.id.room_status);
            roomImage = itemView.findViewById(R.id.room_image);
            roomLinearLayout = itemView.findViewById(R.id.room_layout);
            roomDeleteButton = itemView.findViewById(R.id.room_delete_button);
        }
    }

}
