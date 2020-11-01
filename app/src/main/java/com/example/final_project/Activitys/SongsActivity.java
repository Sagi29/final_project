package com.example.final_project.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Data.Adapter_Song;
import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.Interfaces.MyCallback;
import com.example.final_project.Data.Song;
import com.example.final_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SongsActivity extends AppCompatActivity {

    private RecyclerView song_RCV_Songs;
    private Button song_BTN_back;
    private EditText song_EDT_length;
    private EditText song_EDT_songName;
    private Button song_BTN_addSong;

    private int isAdmin;
    private String name;
    private ArrayList<Song> songList = new ArrayList<>();

    private DatabaseReference myRefSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        song_RCV_Songs = findViewById(R.id.song_RCV_Songs);
        song_BTN_back = findViewById(R.id.song_BTN_back);
        song_EDT_length = findViewById(R.id.song_EDT_length);
        song_EDT_songName= findViewById(R.id.song_EDT_songName);
        song_BTN_addSong= findViewById(R.id.song_BTN_addSong);

        isAdmin = getIntent().getIntExtra(Constants.IS_USER_ADMIN,0);
        if(isAdmin == 0) { //Not Admin
            song_BTN_addSong.setVisibility(View.INVISIBLE);
            song_EDT_length.setVisibility(View.INVISIBLE);
            song_EDT_songName.setVisibility(View.INVISIBLE);
        }

        song_BTN_addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSongToFirebase();
            }
        });
        song_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = getIntent().getStringExtra(Constants.KINDERGARTEN_NAME);
        myRefSong = FireBase.getInstance().getReference(Constants.KINDERGARTEN_PATH).child(name).child("songList");

        updateRecyclerView();
    }
    private void updateRecyclerView() {
        songList.clear();
        readSongList(new MyCallback() {

            @Override
            public void onCallback(Object song) {
                songList.add((Song) song);
                Adapter_Song adapter_song = new Adapter_Song(SongsActivity.this, songList);
                song_RCV_Songs.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                song_RCV_Songs.setAdapter(adapter_song);
            }
        });
    }

    private void addSongToFirebase() {
        if(song_EDT_songName.getText().toString().matches("")||
            song_EDT_length.getText().toString().matches("")){
            Toast.makeText(SongsActivity.this, "Error! Fill all fields", Toast.LENGTH_LONG).show();
        }
        else{
            String s = song_EDT_songName.getText().toString();
            String ss = song_EDT_length.getText().toString();
            song_EDT_songName.setText("");
            song_EDT_length.setText("");
            float f = Float.parseFloat(ss);
            Song song = new Song(s,f);
            songList.add(song);
            myRefSong.setValue(songList);
            Toast.makeText(SongsActivity.this, "upload song successfully", Toast.LENGTH_LONG).show();
            updateRecyclerView();
        }
    }

    private void readSongList(final MyCallback myCallback) {
        myRefSong.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> tempList  = new ArrayList<String>();
            Song song;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    song = snapshot.getValue(Song.class);

                    myCallback.onCallback(song);
                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("pttt", "Failed to read value.", error.toException());
            }
        });

    }


}