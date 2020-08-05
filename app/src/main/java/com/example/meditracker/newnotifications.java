package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class newnotifications extends ListActivity implements AdapterView.OnItemSelectedListener {

    private TextView selection;            ///////des1 representing the first description of the item present and the medicine for which this notification is made..
    private Button savenotifs;             //// des2 represents the date this notification was made
    private Button backtolist;
    medicaldatabase db=new medicaldatabase(newnotifications.this);           //// after getting all the details regarding the notification times we add all those in a databse her itself
    List<String>items=new ArrayList<String>();
    List<String>times=new ArrayList<String>();   ///// this stores all the times in a string array list....
    private ImageButton addtimer;
    private String medicinename;
    private String str = "";
    private int flag=0;
    private int flag_add=0;
    ArrayAdapter<String> adapter;

    notificationsdatabase db2=new notificationsdatabase(newnotifications.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newnotifications);
    selection=(TextView)findViewById(R.id.medicine_name);
    Spinner spin=(Spinner)findViewById(R.id.spinner);
    savenotifs=(Button)findViewById(R.id.savenotifs);
    backtolist=(Button)findViewById(R.id.backtolist);
    addtimer=(ImageButton)findViewById(R.id.addtimer);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                times){
            @Override

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.YELLOW);
                text.setTextSize(27);
                text.setPadding(30,10,0,0);
                text.setCursorVisible(false);
                text.setTypeface(null,Typeface.BOLD);
                text.setBackgroundColor(R.drawable.backgroundshadow);
                text.setHeight(200);
                return view;
            }
        };
        setListAdapter(adapter);
    addtimer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent go=new Intent(newnotifications.this,settimer.class);
            go.putExtra("time",String.valueOf(flag_add));
            startActivityForResult(go,10000);     //// request code is 1000 over here..
        }
    });
    backtolist.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent();
            setResult(100,i);
            finish();
        }
    });
    savenotifs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(times.size()==0)
            {
                Toast.makeText(newnotifications.this,"Please select a time for notification",Toast.LENGTH_SHORT).show();
            }
            else if (flag==0)
            {
                flag_add++;
                Calendar calendar=Calendar.getInstance();
                String curr_date= DateFormat.getDateInstance().format(calendar.getTime());
                System.out.println(curr_date);
                int z=db2.countrows()+1;
                boolean b = db2.addnotificationdetails(medicinename, str,z,curr_date); /////////here we successfully added the new notification to the data base..
                if (b == true){
                    Toast.makeText(newnotifications.this, "Added details to database", Toast.LENGTH_SHORT).show();
                flag++;}
                else
                    Toast.makeText(newnotifications.this, "Couldnot add details to database", Toast.LENGTH_SHORT).show();
            }
            else if(flag>0)
                {
                    flag_add++;
                    int z=db2.countrows();
                    System.out.println(z);
                boolean u=db2.updatenotificationdetails(medicinename,str,z);
                if (u)
                    Toast.makeText(newnotifications.this, "Updated details to database", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(newnotifications.this, "Couldnot update details to database", Toast.LENGTH_SHORT).show();
                }

        }
    });
    Cursor c=db.displaydetails();
    c.moveToLast();
    if(c==null)
    {
        Toast.makeText(this,"No Medicine in the Database yet..",Toast.LENGTH_SHORT).show();
        finish();
    }
    do {
        items.add(c.getString(2));
    }
    while(c.moveToPrevious());
    spin.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,                 /////the default layout was overriden in here so we are able to customise it..
                items){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextSize(27);
                        text.setTypeface(null,Typeface.BOLD);
                        text.setCursorVisible(false);
                        return view;
                    }
                                            ///////// the toughest part is how do we create the list items containing all the items stored..
        };
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        medicinename=items.get(position);
        selection.setText("Medicine Name - "+ items.get(position));
        savenotifs.setEnabled(true);/////////////////
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selection.setText("Medicine Name - Unassigned");
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent pData){
        super.onActivityResult(requestCode, resultCode, pData);
        if(requestCode==10000){
            String a=pData.getStringExtra("DATE");
            times.add(a);               //// so our time list is ready now all we have to do is to fit them in database for viewing purpose..
            adapter.notifyDataSetChanged();
            String strSeparator = ",";
            str="";
            for (int i = 0;i<times.size(); i++) {
                str = str+times.get(i);
                // Do not append comma at the end of last element
                if(i<times.size()-1){
                    str = str+strSeparator;
                }
            }
        }
    }
}