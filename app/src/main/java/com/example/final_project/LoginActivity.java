package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText login_EDT_IDNumber;
    private EditText login_EDT_KidengardenName;
    private EditText login_EDT_password;
    private Button login_BTN_login;
    private Button login_BTN_toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsByID();
    }

    private void findViewsByID() {
        login_EDT_IDNumber = findViewById(R.id.login_EDT_IDNumber);
        login_EDT_KidengardenName = findViewById(R.id.login_EDT_KidengardenName);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_toRegister = findViewById(R.id.login_BTN_toRegister);
    }
}