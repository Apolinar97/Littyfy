package com.example.littyfy_v0;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                builder.setTitle("Enter Song Title");

                final EditText songNameField = new EditText(RoomActivity.this);
                songNameField.setHint("Artist - Title");
                builder.setView(songNameField);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String songTitle = songNameField.getText().toString();

                        if (TextUtils.isEmpty(songTitle))
                        {
                            Toast.makeText(RoomActivity.this, "Please enter class name", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            PostSong(songTitle);
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

    private void PostSong(final String songName)
    {
        RootRef.child("Rooms").child(roomName).child("Requests").child(songName).setValue(songName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(RoomActivity.this, songName + " was created successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
