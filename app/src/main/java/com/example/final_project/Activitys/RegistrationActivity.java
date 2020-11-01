package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.Data.MySP;
import com.google.gson.Gson;
import android.content.Intent;
import android.os.Bundle;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Data.Constants;
import com.example.final_project.R;
import com.example.final_project.Data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {


    private EditText registration_EDT_parentName;

    private EditText registration_EDT_childName;
    private EditText registration_EDT_kindergartenName;
    private EditText registration_EDT_email;
    private EditText registration_EDT_password;
    private Button registration_BTN_done;
    private Button registration_BTN_exit;
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
        //registration_EDT_password.setTransformationMethod(new PasswordTransformationMethod());

        registration_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
    }

    private View.OnClickListener doneButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            checkFieldsText();
        }
    };

    private void checkFieldsText() {
        if(registration_EDT_email.getText().toString().matches("") ||
                registration_EDT_kindergartenName.getText().toString().matches("")||
                registration_EDT_childName.getText().toString().matches("")||
                registration_EDT_parentName.getText().toString().matches("")||
                registration_EDT_password.getText().toString().matches(""))
        {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_LONG).show();
        }
        else
        {
            final String name = registration_EDT_parentName.getText().toString();
            final String childName = registration_EDT_childName.getText().toString();
            final String kindergarten = registration_EDT_kindergartenName.getText().toString();
            final String email = registration_EDT_email.getText().toString();
            final String pass = registration_EDT_password.getText().toString();
            final byte isAdmin;
            if(registration_CKB_isAdmin.isChecked())
                isAdmin = 1; //Admin
            else
                isAdmin = 0; //Not Admin


            myAuth.createUserWithEmailAndPassword(email,pass).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser firebaseUser = myAuth.getCurrentUser();

                                    String idUser = firebaseUser.getUid();

                                    //myRefUsers.child("email").setValue(new User(name,email,childName,kindergarten,isAdmin,pass));
                                    User u = new User(idUser,name,email,childName,kindergarten,isAdmin,pass,"Empty");
                                    Gson gson = new Gson();
                                    String json = gson.toJson(u);
                                    mySP.putString(Constants.USER_DATA,json);


                                    Intent intent = new Intent(RegistrationActivity.this, MainPageActivity.class);
                                    intent.putExtra(Constants.PREVIOUS_ACTIVITY_NAME,"Registration");
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        }

    }

    private void findViewsByID() {

        registration_EDT_parentName = findViewById(R.id.registration_EDT_parentName);
        registration_EDT_childName = findViewById(R.id.registration_EDT_childName);
        registration_EDT_kindergartenName = findViewById(R.id.registration_EDT_kidengardenName);
        registration_EDT_email = findViewById(R.id.registration_EDT_email);
        registration_EDT_password = findViewById(R.id.registration_EDT_password);
        registration_CKB_isAdmin = findViewById(R.id.registration_CKB_isAdmin);
        registration_BTN_done = findViewById(R.id.registration_BTN_done);
        registration_BTN_exit = findViewById(R.id.registration_BTN_exit);
    }
}