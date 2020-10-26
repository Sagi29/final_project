package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.Data.MySP;
import com.google.gson.Gson;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.final_project.Data.Constants;
import com.example.final_project.R;
import com.example.final_project.Data.User;
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


public class RegistrationActivity extends AppCompatActivity {


    private EditText registration_EDT_parentName;
    //private EditText registration_EDT_IDNumber;
    private EditText registration_EDT_childName;
    private EditText registration_EDT_kindergartenName;
    private EditText registration_EDT_email;
    private EditText registration_EDT_password;
    private RadioButton registration_RDB_yes;
    private RadioButton registration_RDB_no;
    private Button registration_BTN_done;
    private CheckBox registration_CKB_isAdmin;

    private User userData;
    private MySP mySP;
    private FirebaseAuth myAuth;
    private FirebaseDatabase database;
    private  DatabaseReference myRefUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewsByID();
        mySP = new MySP(this);
        database = FirebaseDatabase.getInstance();
        myRefUsers = database.getReference(Constants.USER_PATH);
        myAuth = FirebaseAuth.getInstance();
        registration_BTN_done.setOnClickListener(doneButtonClicked);
        Log.d("ptt","IN RegistrationActivity");
    }

    private View.OnClickListener doneButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            checkFillingtext();
        }
    };

    private void checkFillingtext() {
        if(registration_EDT_email.getText().toString().matches("") ||
                registration_EDT_kindergartenName.getText().toString().matches("")||
                registration_EDT_childName.getText().toString().matches("")||
                registration_EDT_parentName.getText().toString().matches("")||
                registration_EDT_password.getText().toString().matches(""))
        {
            Toast.makeText(this, "plase fill all feilds ", Toast.LENGTH_LONG).show();
        }
        else
        {
            final String name = registration_EDT_parentName.getText().toString();
            final String childName = registration_EDT_childName.getText().toString();
            final String kindergarten = registration_EDT_kindergartenName.getText().toString();
            final String email = registration_EDT_email.getText().toString();
            final String pass = registration_EDT_password.getText().toString();
            final boolean isAdmin;
            Log.d("ptt","registration_CKB_isAdmin.isChecked() ->" + registration_CKB_isAdmin.isChecked());
            if(registration_CKB_isAdmin.isChecked())
                isAdmin = true;
            else
                isAdmin = false;


            myAuth.createUserWithEmailAndPassword(email,pass).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser firebaseUser = myAuth.getCurrentUser();

                                    String idUser = firebaseUser.getUid();

                                    //myRefUsers.child("email").setValue(new User(name,email,childName,kindergarten,isAdmin,pass));
                                    User u = new User(idUser,name,email,childName,kindergarten,isAdmin,pass);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(u);
                                    mySP.putString(Constants.USER_DATA,json);


                                    Intent intent = new Intent(RegistrationActivity.this, MainPageActivity.class);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        }



    }

    private void getUserData(String idUser) {

        myRefUsers.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("ptt", "IN dataSnapshot RRRRRR ");
                User u = dataSnapshot.getValue(User.class);
                Log.d("ptt", "Value of email is : RRRRRRR   " + u.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ptt", "Failed to read value.", error.toException());
            }
        });
    }



    private void addToDataBase(String name) {

    }

    private void findViewsByID() {

        registration_EDT_parentName = findViewById(R.id.registration_EDT_parentName);
        //registration_EDT_IDNumber = findViewById(R.id.registration_EDT_IDNumber);
        registration_EDT_childName = findViewById(R.id.registration_EDT_childName);
        registration_EDT_kindergartenName = findViewById(R.id.registration_EDT_kidengardenName);
        registration_EDT_email = findViewById(R.id.registration_EDT_email);
        registration_EDT_password = findViewById(R.id.registration_EDT_password);
       // registration_RDB_yes = findViewById(R.id.registration_RDB_yes);
       // registration_RDB_no = findViewById(R.id.registration_RDB_no);
        registration_CKB_isAdmin = findViewById(R.id.registration_CKB_isAdmin);
        registration_BTN_done = findViewById(R.id.registration_BTN_done);
    }
}