package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.Data.Kindergarten;
import com.example.final_project.Data.User;
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

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText login_EDT_email;
    private EditText login_EDT_KindergartenName;
    private EditText login_EDT_password;
    private Button login_BTN_login;
    private Button login_BTN_toRegister;

    private FirebaseAuth myRefAuth;
    private DatabaseReference myRefUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsByID();
        Log.v("tag","asdffffffgggggghhhh");
        myRefAuth = FirebaseAuth.getInstance();
        myRefUser = FireBase.getInstance().getReference(Constants.USER_PATH);
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
        myRefAuth.signInWithEmailAndPassword(login_EDT_email.getText().toString(),login_EDT_password.getText().toString()).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = myRefAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "signInWithEmail:success", Toast.LENGTH_LONG).show();
                                String uidUser = user.getUid();
                                getUserData(uidUser);
                                

                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                            }


                        }

                    });
    }

    private void getUserData(String uidUser) {
        myRefUser.child(uidUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User u = dataSnapshot.getValue(User.class);
                Log.d("ptt", " myRefUser :->  " + myRefUser);
                Log.d("ptt", "Value in user is: " + u.getEmail());

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
    }
}