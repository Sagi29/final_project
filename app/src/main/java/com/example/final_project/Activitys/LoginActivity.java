package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Data.Adapter_Song;
import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.Data.Kindergarten;
import com.example.final_project.Data.MySP;
import com.example.final_project.Data.Song;
import com.example.final_project.Data.User;
import com.example.final_project.Interfaces.MyCallback;
import com.example.final_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText login_EDT_email;
    private EditText login_EDT_KindergartenName;
    private EditText login_EDT_password;
    private Button login_BTN_login;
    private Button login_BTN_toRegister;
    private Button login_BTN_exit;

    //private User userData;
    private MySP mySP;
    private FirebaseAuth myRefAuth;
    private DatabaseReference myRefUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsByID();
        mySP = new MySP(this);
        myRefAuth = FirebaseAuth.getInstance();
        myRefUser = FireBase.getInstance().getReference(Constants.USER_PATH);
        login_BTN_login.setOnClickListener(loginToSystem);

        //login_EDT_password.setTransformationMethod(new PasswordTransformationMethod());

        login_BTN_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
    }




    private View.OnClickListener loginToSystem = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if(login_EDT_email.getText().toString().matches("") ||
                    login_EDT_password.getText().toString().matches("") ||
                    login_EDT_KindergartenName.getText().toString().matches("") ){

                Toast.makeText(LoginActivity.this, "Eror!! All fields must be filled out\n ", Toast.LENGTH_LONG).show();
            }
            else{
                loginSystem();
            }
        }
    };

    private void loginSystem() {
        myRefAuth.signInWithEmailAndPassword(login_EDT_email.getText().toString(),login_EDT_password.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = myRefAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "signInWithEmail:success", Toast.LENGTH_LONG).show();
                                String uidUser = user.getUid();

                                getUserData(uidUser,new MyCallback(){

                                    @Override
                                    public void onCallback(Object user) {

                                        Gson gson = new Gson();
                                        String json = gson.toJson((User)user);
                                        mySP.putString(Constants.USER_DATA, json);
                                        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            }

                        }

                    });
    }

    private void getUserData(String uidUser, final MyCallback myCallback) {
        myRefUser.child(uidUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userData = dataSnapshot.getValue(User.class);
                myCallback.onCallback(userData);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("pttt", "Failed to read value.", error.toException());
            }
        });
    }

    private void findViewsByID() {
        login_EDT_email = findViewById(R.id.login_EDT_email);
        login_EDT_KindergartenName = findViewById(R.id.login_EDT_KindergartenName);
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_BTN_toRegister = findViewById(R.id.login_BTN_toRegister);
        login_BTN_exit = findViewById(R.id.login_BTN_exit);
    }
}