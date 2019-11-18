package com.example.littyfy_v0;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class RoomActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    public String roomName;

    private FloatingActionButton suggestSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        roomName = getIntent().getExtras().get("roomName").toString();

        Toolbar mToolbar = findViewById(R.id.room_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(roomName);

        ViewPager mViewPager = findViewById(R.id.room_tabs_pager);
        TabsAdapter mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsAdapter);

        TabLayout mTabLayout = findViewById(R.id.room_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        suggestSong = findViewById(R.id.fab_suggest_song);

        suggestSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this, R.style.AlertDialog);
                builder.setTitle("Enter Track Info");


                LinearLayout layout = new LinearLayout(RoomActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText songNameField = new EditText(RoomActivity.this);
                final EditText artistNameField = new EditText(RoomActivity.this);

                songNameField.setHint("Track Name");
                artistNameField.setHint("Artist Name");

                layout.addView(songNameField);
                layout.addView(artistNameField);
                builder.setView(layout);

                builder.setPositiveButton("Suggest", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String songTitle = songNameField.getText().toString();
                        String artistName = artistNameField.getText().toString();

                        if (TextUtils.isEmpty(songTitle))
                        {
                            Toast.makeText(RoomActivity.this, "Please enter song title", Toast.LENGTH_SHORT).show();

                            if (TextUtils.isEmpty(artistName))
                            {
                                Toast.makeText(RoomActivity.this, "Please enter artist name", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            PostSong(songTitle, artistName);
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
        });


    }

    private void PostSong(final String songName, final String artistName)
    {
        String songKey = RootRef.child("Rooms").child(roomName).child("Requests").push().getKey();
        HashMap<String, Object> songInfoMap = new HashMap<>();
        songInfoMap.put("title", songName);
        songInfoMap.put("artist", artistName);

        RootRef.child("Rooms").child(roomName).child("Requests").child(songKey).updateChildren(songInfoMap);
    }
}
