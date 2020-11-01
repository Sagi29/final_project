package com.example.final_project.Activitys;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.Data.AdapterFetch;
import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FetchFileActivity extends AppCompatActivity {

    private RecyclerView fetchFile_RCV_filesList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_file);

        String kName = getIntent().getStringExtra(Constants.KINDERGARTEN_NAME);
        DatabaseReference databaseReference = FireBase.getInstance().getReference(Constants.KINDERGARTEN_PATH).child(kName).child("fileList");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String fileName =   snapshot.getKey();
                String url = snapshot.getValue(String.class);
                ((AdapterFetch)fetchFile_RCV_filesList.getAdapter()).update(fileName,url);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fetchFile_RCV_filesList = findViewById(R.id.fetchFile_RCV_filesList);
        fetchFile_RCV_filesList.setLayoutManager(new LinearLayoutManager(FetchFileActivity.this));
        AdapterFetch adapterFetch = new AdapterFetch(fetchFile_RCV_filesList,FetchFileActivity.this, new ArrayList<String>(),new ArrayList<String>());
        fetchFile_RCV_filesList.setAdapter(adapterFetch);

    }
}
