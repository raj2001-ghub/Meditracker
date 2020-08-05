package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class meddetails extends AppCompatActivity {

    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private EditText ed5;
    private EditText ed6;
    private Button b;
    private medicaldatabase dbm=new medicaldatabase(meddetails.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meddetails);
        ed1=(EditText)findViewById(R.id.mednamedet);
        ed2=(EditText)findViewById(R.id.illnessnamedet);
        ed3=(EditText)findViewById(R.id.datedet);
        ed4=(EditText)findViewById(R.id.pillsdet);
        ed5=(EditText)findViewById(R.id.pricedet);
        ed6=(EditText)findViewById(R.id.docnamedet);
        b=(Button)findViewById(R.id.backtor);
        Intent i=getIntent();
        String pos=i.getStringExtra("POSITION");
        int position=Integer.parseInt(pos);
        ///////////////////////////////////////////Now we call a function from the database which sees that row of the database
        Cursor c=dbm.displaydetails();
        c.moveToPosition(position);
        ed1.append(c.getString(2));
        ed2.append(c.getString(3));
        ed3.append(c.getString(4));
        ed4.append(c.getString(6));
        ed5.append(c.getString(5));
        ed6.append(c.getString(1));    ////////////////////////////in future when all the details are not mandatory those which are not mandatory should be written as not mentioned..
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}