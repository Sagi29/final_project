package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

public class RegistrationActivity extends AppCompatActivity {

    private EditText registration_EDT_parentName;
    private EditText registration_EDT_IDNumber;
    private EditText registration_EDT_childName;
    private EditText registration_EDT_kidengardenName;
    private EditText registration_EDT_password;
    private RadioButton registration_RDB_yes;
    private RadioButton registration_RDB_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewsByID();
    }

    private void findViewsByID() {

        registration_EDT_parentName = findViewById(R.id.registration_EDT_parentName);
        registration_EDT_IDNumber = findViewById(R.id.registration_EDT_IDNumber);
        registration_EDT_childName = findViewById(R.id.registration_EDT_childName);
        registration_EDT_kidengardenName = findViewById(R.id.registration_EDT_kidengardenName);
        registration_EDT_password = findViewById(R.id.registration_EDT_password);
        registration_RDB_yes = findViewById(R.id.registration_RDB_yes);
        registration_RDB_no = findViewById(R.id.registration_RDB_no);
    }
}