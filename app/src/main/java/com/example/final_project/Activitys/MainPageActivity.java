package com.example.final_project.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.Data.Kindergarten;
import com.example.final_project.Data.MySP;
import com.example.final_project.Data.Song;
import com.example.final_project.Data.User;
import com.example.final_project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity {

    private final int RESULT_LOAD_FILE = 1;

   private EditText mainPage_EDT_date ;
   private EditText mainPage_EDT_updates;
   private Button mainPage_BTN_calendar;
   private Button mainPage_BTN_personalFile;
   private Button mainPage_BTN_songs;
   private Button mainPage_BTN_worksheets;
   private Button mainPage_BTN_uploadWorksheets;
   private Button mainPage_BTN_signOut;

   private MySP mySP;
   private User currentUserData;
   private Kindergarten currentKindergartenData;

   private DatabaseReference myRefUsers;
   private FirebaseDatabase database;
   private StorageReference storageRef;
   private DatabaseReference myRefKindergarten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        findViewsByID();
        mySP = new MySP(this);
        database = FirebaseDatabase.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        myRefUsers = database.getReference(Constants.USER_PATH);
        myRefKindergarten = database.getReference(Constants.KINDERGARTEN_PATH);


        //get current user info & save in database
        Gson gson = new Gson();
        String string = mySP.getString(Constants.USER_DATA,"");
        currentUserData = gson.fromJson(string, User.class);
        String previousActivity = getIntent().getStringExtra(Constants.PREVIOUS_ACTIVITY_NAME);

        if(previousActivity != null){ //came from RegistrationActivity
            /* ---  Add user to firebase --- */
            myRefUsers.child(currentUserData.getUid()).setValue(currentUserData);
            // writeUserData();
        }
        myRefKindergarten = database.getReference(Constants.KINDERGARTEN_PATH).child(currentUserData.getKindergartenName());


        getKindergartenData();
        if(currentKindergartenData != null)
            Log.d("pkk","currentKindergartenData.getName() ->" + currentKindergartenData.getName());
        addKindergarten();

        //set Date of today.
        mainPage_EDT_date.setText(Calendar.getInstance().getTime().toString());
        //set update of week.
        mainPage_EDT_updates.setText("Updates is will come");

        mainPage_BTN_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        mainPage_BTN_calendar.setOnClickListener(buttonClicked);
        mainPage_BTN_worksheets.setOnClickListener(buttonClicked);
        mainPage_BTN_personalFile.setOnClickListener(buttonClicked);
        mainPage_BTN_songs.setOnClickListener(buttonClicked);
        mainPage_BTN_uploadWorksheets.setOnClickListener(buttonClicked);
        Toast.makeText(MainPageActivity.this, "Main  is admin ->"+currentUserData.getAdmin(),Toast.LENGTH_SHORT).show();
        Log.d("ptt","currentUserData.isAdmin() -> " + currentUserData.getAdmin());
        if(currentUserData.getAdmin() == 1)
            mainPage_BTN_uploadWorksheets.setVisibility(View.VISIBLE);
        else
            mainPage_BTN_uploadWorksheets.setVisibility(View.INVISIBLE);
    }

    private void getKindergartenData() {

        myRefKindergarten.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentKindergartenData = snapshot.getValue(Kindergarten.class);
                        if(currentKindergartenData != null)
                            Log.d("ptt","getKindergartenData - > addListenerForSingleValueEvent :  " + currentKindergartenData.getKindergartenTeacher());
                        else
                            Log.d("ptt","getKindergartenData - > addListenerForSingleValueEvent :  not read!!");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("ptt", "Failed to read Kindergarten.", error.toException());
                    }
                });

    }

    private void writeUserData() {

        myRefUsers.child(currentUserData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("ptt", "IN dataSnapshot !!! ");
                currentUserData = dataSnapshot.getValue(User.class);
                Log.d("ptt", "Value of email is : " + currentUserData.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("ptt", "Failed to read User.", error.toException());
            }
        });
    }

    private View.OnClickListener buttonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            if (((String) view.getTag()).equals(mainPage_BTN_calendar.getTag().toString()))
                openCalendarActivity();
            else
                if (((String) view.getTag()).equals(mainPage_BTN_personalFile.getTag().toString()))
                openPersonalFileActivity();
            else
                if (((String) view.getTag()).equals(mainPage_BTN_songs.getTag().toString()))
                openSongsActivity();

                else
                if (((String) view.getTag()).equals(mainPage_BTN_worksheets.getTag().toString()))
                    openWorkSheetsActivity();
                else
                if (((String) view.getTag()).equals(mainPage_BTN_uploadWorksheets.getTag().toString()))
                    upload();
                    //uploadWorkSheets();



        }
    };

    private void upload() {
        Intent intent = new Intent(this, UploadFileActivity.class);
        startActivity(intent);
    }

   /* private void uploadWorkSheets() {

        Log.d("ptt","IN uploadWorkSheets");
        Intent intent = new Intent();
        intent.setType("");
        intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,30);
    }*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK){
            StorageReference pathToUpload;
            if(data.getClipData() != null){

                Log.d("ptt","IN onActivityResult");
                int totalItemsSelected = data.getClipData().getItemCount();


                for(int i = 0 ; i<totalItemsSelected; i++){
                    Log.d("ptt","IN for with index: "+i);
                    *//*Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                    uploadTask = riversRef.putFile(file);
                    Uri.fro*//*
                    Uri uriFile = data.getClipData().getItemAt(i).getUri();
                    String fileName = getFileName(uriFile);
                    uriFile = Uri.fromFile(new File(fileName));
                    pathToUpload = storageRef.child("kindergarten").child("WorkSheets").child(fileName);
                    UploadOneFile(pathToUpload,uriFile);
                }

                Log.d("ptt", "SELECT MULTY File ");
            }
            else if(data.getData() != null){
                Uri uriFile = data.getClipData().getItemAt(0).getUri();
                String fileName = getFileName(uriFile);
                pathToUpload = storageRef.child("kindergarten").child("WorkSheets").child(fileName);
                UploadOneFile(pathToUpload,uriFile);
                Log.d("ptt", "SELECT one File ");
            }
        }
    }
*/
 /*   private void UploadOneFile(StorageReference pathToUpload, Uri uriFile) {
        pathToUpload.putFile(uriFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainPageActivity.this,"Done Uploading",Toast.LENGTH_LONG).show();
            }
        });
    }*/

    private void openWorkSheetsActivity() {

    }

    private void openSongsActivity() {
        Intent intent = new Intent(this, SongsActivity.class);
        intent.putExtra(Constants.KINDERGARTEN_NAME,currentUserData.getKindergartenName());
        startActivity(intent);
    }

    private void openPersonalFileActivity() {
        Intent intent = new Intent(this, PersonalFileActivity.class);

        startActivity(intent);
    }

    private void openCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        Log.d("ptt","IN openCalendarActivity-> "+currentUserData.getAdmin());
        intent.putExtra(Constants.IS_USER_ADMIN, currentUserData.getAdmin());
        intent.putExtra(Constants.KINDERGARTEN_NAME,currentUserData.getKindergartenName());
        startActivity(intent);
    }




    public String getFileName(Uri uri){
        String res = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally{
                cursor.close();
            }
        }
        if(res == null){
            res = uri.getPath();
            int i = res.lastIndexOf('/');
            if(i != -1){
                res = res.substring(i+1);
            }
        }
        return res;
    }

    private void addKindergarten() {
        FireBase fb = new FireBase();
        fb.setDatabase(FirebaseDatabase.getInstance());


        String updates = "update...";
        ArrayList<Song> songList = new ArrayList<>();
        Map<String,String> eventMap = new HashMap<String, String>();
        Kindergarten kindergarten = new Kindergarten(currentUserData.getKindergartenName(), currentUserData.getName(),updates,songList,eventMap);
        kindergarten.getSongList().add(new Song("Aviv",3.24f));
        kindergarten.getSongList().add(new Song("Peshach",2.16f));
        kindergarten.getSongList().add(new Song("Summer",4.06f));


        kindergarten.getEventMap().put("24-10-2020","Holeday");
        kindergarten.getEventMap().put("23-10-2020","Holeday");

        DatabaseReference mykindergartenRef = fb.getDatabase().getReference(Constants.KINDERGARTEN_PATH);
        mykindergartenRef.child(kindergarten.getName()).setValue(kindergarten);

    }

    private void findViewsByID() {

        mainPage_EDT_date = findViewById(R.id.mainPage_EDT_date);
        mainPage_EDT_updates= findViewById(R.id.mainPage_EDT_updates);
        mainPage_BTN_calendar= findViewById(R.id.mainPage_BTN_calendar);
        mainPage_BTN_personalFile= findViewById(R.id.mainPage_BTN_personalFile);
        mainPage_BTN_songs= findViewById(R.id.mainPage_BTN_songs);
        mainPage_BTN_worksheets= findViewById(R.id.mainPage_BTN_worksheets);
        mainPage_BTN_uploadWorksheets = findViewById(R.id.mainPage_BTN_uploadWorksheets);
        mainPage_BTN_signOut = findViewById(R.id.mainPage_BTN_signOut);
    }
}