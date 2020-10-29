package com.example.final_project.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private RecyclerView song_LST_Songs;
    private Button song_BTN_back;
    private String name;
    private ArrayList<Song> songList = new ArrayList<>();

    private DatabaseReference myRefSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        song_LST_Songs = findViewById(R.id.song_LST_Songs);
        song_BTN_back = findViewById(R.id.song_BTN_back);
        song_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*songList.add(new Song("Bdika 1",3.00f));
        songList.add(new Song("Bdika 2",2.12f));
        songList.add(new Song("Bdika 3",1.11f));*/
        name = getIntent().getStringExtra(Constants.KINDERGARTEN_NAME);
        myRefSong = FireBase.getInstance().getReference(Constants.KINDERGARTEN_PATH).child(name).child("songList");
        Log.d("ptt","SongsActivity   myRefSong -> " + myRefSong);


        readSongList(new MyCallback(){

            @Override
            public void onCallback(Object song) {
                songList.add((Song)song);
                Adapter_Song adapter_song = new Adapter_Song(SongsActivity.this, songList);
                song_LST_Songs.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                song_LST_Songs.setAdapter(adapter_song);
            }
        });

    }

    private void readSongList(final MyCallback myCallback) {
        myRefSong.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> tempList  = new ArrayList<String>();
            Song song;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d("ptt","SongsActivity   snapshot -> " + snapshot+ "   snapshot.getKey() == " + snapshot.getKey());
                    song = snapshot.getValue(Song.class);
                    Log.d("ptt","SongsActivity   s -> " + song);
                    myCallback.onCallback(song);
                }
                //songList = dataSnapshot.getValue(ArrayList.class);

                //Log.d("ptt", "Value in ArrayList is: " + songList.toString());

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("pttt", "Failed to read value.", error.toException());
            }
        });

    }


}