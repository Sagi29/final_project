package com.example.final_project.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.Data.Constants;
import com.example.final_project.Data.FireBase;
import com.example.final_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendar_CLV_calendarView;
    private TextView calendar_EDT_event;
    private Button calendar_BTN_addEvent;
    private Button calendar_BTN_back;

    private DatabaseReference myRefKindergarten;
    private String date;
    private int isAdmin;
    private String kindergartenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        findViewsById();

        isAdmin = getIntent().getIntExtra(Constants.IS_USER_ADMIN,0);

        kindergartenName = getIntent().getStringExtra(Constants.KINDERGARTEN_NAME);
        myRefKindergarten = FireBase.getInstance().getReference(Constants.KINDERGARTEN_PATH).child(kindergartenName).child("eventMap");
        if(isAdmin==1) {
            calendar_BTN_addEvent.setVisibility(View.VISIBLE);
        }
        else {
            calendar_EDT_event.setEnabled(false);
            calendar_BTN_addEvent.setVisibility(View.INVISIBLE);
        }

        calendar_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        calendar_BTN_addEvent.setOnClickListener(addEventButtonClicked);
        calendar_CLV_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                //calendar_EDT_event.setText("Date: " + i2 + " / " + i1 + " / " + i);
                date =  i2 + "-" + i1 + "-" + i;
                Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_SHORT).show();

                myRefKindergarten.child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String s = dataSnapshot.getValue(String.class);
                        Log.d("ptt", "  myRefKindergarten.child(date) :->  " + myRefKindergarten.child(date));
                        Log.d("ptt", "Value in event map is: " + s);
                        if(s != null)
                            calendar_EDT_event.setText(s);
                        else
                            calendar_EDT_event.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("pttt", "Failed to read value.", error.toException());
                    }
                });

            }
        });

    }

    private View.OnClickListener addEventButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (calendar_EDT_event.getText() != "") {
                String s = calendar_EDT_event.getText().toString();
                myRefKindergarten.child(date).setValue(s);
                Toast.makeText(CalendarActivity.this, "Add Event To this Date: success", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(CalendarActivity.this, "Error! Add event to text", Toast.LENGTH_LONG).show();

        }
    };

    private void findViewsById() {
        calendar_CLV_calendarView = findViewById(R.id.calendar_CLV_calendarView);
        calendar_EDT_event = findViewById(R.id.calendar_EDT_event);
        calendar_BTN_addEvent = findViewById(R.id.calendar_BTN_addEvent);
        calendar_BTN_back = findViewById(R.id.calendar_BTN_back);
    }
}