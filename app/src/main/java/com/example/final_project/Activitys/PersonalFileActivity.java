package com.example.final_project.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.R;
import com.google.firebase.database.DatabaseReference;

public class PersonalFileActivity extends AppCompatActivity {

    private EditText personalFile_EDT_comments;
    private Button personalFile_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_file);

        findViewsByID();
        String personalFile = getIntent().getStringExtra(Constants.USER_Uid);

        personalFile_EDT_comments.setText(personalFile);
        personalFile_EDT_comments.setEnabled(false);

        personalFile_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findViewsByID() {
        personalFile_EDT_comments = findViewById(R.id.personalFile_EDT_comments);
        personalFile_BTN_back = findViewById(R.id.personalFile_BTN_back);
    }
}