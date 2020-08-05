package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class addhistory extends AppCompatActivity {

    private EditText q1;
    private EditText q2;
    private EditText q3;
    private EditText q4;
    private EditText q5;
    private EditText q6;
    private ImageButton datepicker;
    private EditText docname;
    private EditText medname;
    private EditText illnessname;
    private EditText date;
    private EditText price;
    private EditText pills;
    private Button add_details;
    private Button back;
    Calendar c;
    DatePickerDialog dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addhistory);
        q1=(EditText)findViewById(R.id.q1);
        q2=(EditText)findViewById(R.id.q2);
        q3=(EditText)findViewById(R.id.q3);
        q4=(EditText)findViewById(R.id.q4);
        q5=(EditText)findViewById(R.id.q5);
        q6=(EditText)findViewById(R.id.q6);
        docname=(EditText)findViewById(R.id.docname);
        medname=(EditText)findViewById(R.id.medname_row);
        illnessname=(EditText)findViewById(R.id.illness_name);
        date=(EditText)findViewById(R.id.date_row);
        datepicker=(ImageButton)findViewById(R.id.datepicker);
        price=(EditText)findViewById(R.id.price_row);
        pills=(EditText)findViewById(R.id.pills);       //here u have to create another button ADD and on clicking it it will go back to previous page..
        add_details=(Button)findViewById(R.id.add_items);

        back=(Button)findViewById(R.id.backb);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);
                dp=new DatePickerDialog(addhistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },day,month,year);
                dp.show();
            }
        });
        docname.addTextChangedListener(logintextwatcher);
        medname.addTextChangedListener(logintextwatcher);
        illnessname.addTextChangedListener(logintextwatcher);
        date.addTextChangedListener(logintextwatcher);
        price.addTextChangedListener(logintextwatcher);
        pills.addTextChangedListener(logintextwatcher);
        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medrecords p=new medrecords(docname.getText().toString(),medname.getText().toString(),illnessname.getText().toString(),date.getText().toString(),
                        Integer.parseInt(price.getText().toString()),Integer.parseInt(pills.getText().toString()));
                medicaldatabase db=new medicaldatabase(addhistory.this);  // THIS IS THE PART WHERE WE ADD TO THE DATABASE.
                boolean success=db.addmedicinedetails(p);
                if(success)
                    Toast.makeText(addhistory.this,"Added Successfully to List",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(addhistory.this,"Addition Failed Please Try Again",Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });

    }
    private TextWatcher logintextwatcher=new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String temp1=docname.getText().toString();
            String temp2=medname.getText().toString();
            String temp3=illnessname.getText().toString();
            String temp4=date.getText().toString();
            String temp5=price.getText().toString();
            String temp6=pills.getText().toString();
            add_details.setEnabled(!temp1.isEmpty()&& !temp2.isEmpty() && !temp3.isEmpty()&& !temp4.isEmpty()&& !temp5.isEmpty() && !temp6.isEmpty() );

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };                                                // THAT IS TO medhistory
}                                                        // create an object of class medrecords and then store that in the database.