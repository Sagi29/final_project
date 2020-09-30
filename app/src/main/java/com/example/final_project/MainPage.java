package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class MainPage extends AppCompatActivity {

   private EditText mainPage_EDT_date ;
   private EditText mainPage_EDT_updates;
   private Button mainPage_BTN_calendar;
   private Button mainPage_BTN_personalFile;
   private Button mainPage_BTN_songs;
   private Button mainPage_BTN_worksheets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        findViewsByID();
        //set Date of today.
        mainPage_EDT_date.setText(Calendar.getInstance().getTime().toString());

        mainPage_BTN_calendar.setOnClickListener(buttonClicked);
        mainPage_BTN_worksheets.setOnClickListener(buttonClicked);
        mainPage_BTN_personalFile.setOnClickListener(buttonClicked);
        mainPage_BTN_songs.setOnClickListener(buttonClicked);
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

        }
    };

    private void openWorkSheetsActivity() {

    }

    private void openSongsActivity() {
        Intent intent = new Intent(this, SongsActivity.class);

        startActivity(intent);
    }

    private void openPersonalFileActivity() {
        Intent intent = new Intent(this, PersonalFile.class);

        startActivity(intent);
    }

    private void openCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);

        startActivity(intent);
    }

    private void findViewsByID() {

        mainPage_EDT_date = findViewById(R.id.mainPage_EDT_date);
        mainPage_EDT_updates= findViewById(R.id.mainPage_EDT_updates);
        mainPage_BTN_calendar= findViewById(R.id.mainPage_BTN_calendar);
        mainPage_BTN_personalFile= findViewById(R.id.mainPage_BTN_personalFile);
        mainPage_BTN_songs= findViewById(R.id.mainPage_BTN_songs);
        mainPage_BTN_worksheets= findViewById(R.id.mainPage_BTN_worksheets);
    }
}