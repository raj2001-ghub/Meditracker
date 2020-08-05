package com.example.meditracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class notificationlist extends AppCompatActivity {

    private int tot_items_list=0;
    private TextView heading;
    private ImageButton addnotif;
    private RecyclerView timelistv;
    private Button button;
    private ArrayList<Object> medicinename,timeconcat,pres_dates; //////// this arrays will contain all the  medicine and timing details of all medicines..
    private notificationsdatabase db3=new notificationsdatabase(notificationlist.this);
    private medicaldatabase db2=new medicaldatabase(notificationlist.this);
    private boolean isitempresent=true;
    customAdapter_times adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationlist);
        heading=(TextView)findViewById(R.id.notifheading);
        addnotif=(ImageButton)findViewById(R.id.addnotif);

        addnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=db2.displaydetails();
                if(cursor.moveToFirst()) {
                    Intent intent = new Intent(notificationlist.this, newnotifications.class);
                    startActivityForResult(intent, 100);
                }
                else
                    Toast.makeText(notificationlist.this,"Please entire medicine into database and then add notifications",Toast.LENGTH_SHORT).show();
            }
        });

        timelistv=(RecyclerView)findViewById(R.id.notificationrecyclerlist);

        medicinename = new ArrayList<>();
        timeconcat = new ArrayList<>();
        pres_dates=new ArrayList<>();
        getnotifdetailsfromdatabase();
        if(isitempresent)
        {
            tot_items_list=tot_items_list+medicinename.size();
            adapter=new customAdapter_times(notificationlist.this,medicinename,timeconcat,pres_dates);
            timelistv.setAdapter(adapter);
            timelistv.post(new Runnable() {
                @Override
                public void run() {
                    timelistv.smoothScrollToPosition(tot_items_list-1);
                }
            });
            timelistv.setLayoutManager(new LinearLayoutManager(notificationlist.this,LinearLayoutManager.VERTICAL,true));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Cursor cursor=db3.displaynotifdetails();
        final int tot_items_db=cursor.getCount();
        if(requestCode==100 && tot_items_db==tot_items_list+1)
        {
            getlastnotifdetailsfromdatabase();
            if(isitempresent) {
                adapter=new customAdapter_times(notificationlist.this,medicinename,timeconcat,pres_dates);
                timelistv.setAdapter(adapter);
                timelistv.post(new Runnable() {
                    @Override
                    public void run() {
                        timelistv.smoothScrollToPosition(tot_items_db-1);
                    }
                });
                timelistv.setLayoutManager(new LinearLayoutManager(notificationlist.this,LinearLayoutManager.VERTICAL,true));
            }
        }

    }

    private void getnotifdetailsfromdatabase() {

        Cursor m=db3.displaynotifdetails();
        if(m.getCount()==0)
            isitempresent=false;
        else
        {
            isitempresent=true;
            while(m.moveToNext())
            {
                medicinename.add(m.getString(1));
                timeconcat.add(m.getString(2));
                pres_dates.add(m.getString(4));
            }
        }
    m.close();
    }
    private void getlastnotifdetailsfromdatabase(){
        Cursor l=db3.displaynotifdetails();
        if(l.getCount()==0)
            isitempresent=false;
        else{
            isitempresent=true;
            l.moveToLast();
            medicinename.add(l.getString(1));
            timeconcat.add(l.getString(2));
            pres_dates.add(l.getString(4));
        }
        l.close();
    }
}