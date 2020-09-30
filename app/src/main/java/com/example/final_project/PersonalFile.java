package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PersonalFile extends AppCompatActivity {

    private EditText personalFile_EDT_comments;
    private Button personalFile_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_file);

        findViewsByID();
    }

    private void findViewsByID() {
        personalFile_EDT_comments = findViewById(R.id.personalFile_EDT_comments);
        personalFile_BTN_back = findViewById(R.id.personalFile_BTN_back);
    }
}