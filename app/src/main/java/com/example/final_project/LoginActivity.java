package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText login_EDT_email;
    private EditText login_EDT_KindergartenName;
    private EditText login_EDT_password;
    private Button login_BTN_login;
    private Button login_BTN_toRegister;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsByID();
        myAuth = FirebaseAuth.getInstance();
        login_BTN_login.setOnClickListener(loginToSystem);
        login_BTN_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private View.OnClickListener loginToSystem = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            loginSystem();
        }
    };

    private void loginSystem() {
        myAuth.signInWithEmailAndPassword(login_EDT_email.getText().toString(),login_EDT_password.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }
                    });
    }

    private void findViewsByID() {
        login_EDT_email = findViewById(R.id.login_EDT_email);
        login_EDT_KindergartenName = findViewById(R.id.login_EDT_KindergartenName);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_toRegister = findViewById(R.id.login_BTN_toRegister);
    }
}