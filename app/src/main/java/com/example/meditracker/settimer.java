package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;                                                       //////  we store the time and medname at this step itself
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class settimer extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {                ///////this is the place where all notification related works has to made and connections should be established

    private TextView timeview;
    private EditText h1;
    private ImageButton clockicon;
    private ImageView clockimage;
    private Button savetime;
    private Button canceltime;
    private Calendar c;
    notificationsdatabase db=new notificationsdatabase(settimer.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settimer);
        timeview=(TextView)findViewById(R.id.timeview);
        h1=(EditText)findViewById(R.id.h1);                       //// u dont have to do anything about this
        clockicon=(ImageButton)findViewById(R.id.clockicon);
        clockimage=(ImageView)findViewById(R.id.clockimage);       /// u dont have to do anything here too
        savetime=(Button)findViewById(R.id.savetime);
        canceltime=(Button)findViewById(R.id.canceltime);
        clockicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        Intent zip=getIntent();
        final String status=zip.getStringExtra("time");        ///////// final keyword is used when we access that inside a class..

        savetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int channel_decider=Integer.parseInt(status);
                if(channel_decider==0)
                startAlarm(c,db.countrows()+1);
                else if(channel_decider>0)
                    startAlarm(c,db.countrows());
                Intent i=new Intent();
                i.putExtra("DATE",DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()));
                setResult(10000,i);
                finish();
            }
        });
        canceltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        savetime.setEnabled(true);
        updateTimeText(c);
    }
    public void updateTimeText(Calendar d){
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(d.getTime());
        timeview.setText(timeText);
    }
    public void startAlarm(Calendar s,int pos){

        System.out.println(pos);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pos, intent, 0);     ////this is the tough part.......
        if (s.before(Calendar.getInstance())) {
            s.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, s.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                     //////first 4 lines are useful for cancelling the alarm
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        timeview.setText("Alarm canceled");
    }
}