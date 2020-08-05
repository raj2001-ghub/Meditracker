package com.example.meditracker;
                                    //////////ONLY ONE THING WE HAVE TO RECTIFY THE BLANK ADDITION CASES WHERE NOTHING IS WRIITEN AND YET IT IS ADDED..
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class medhistory extends AppCompatActivity {

    private int tot_items_list=0;
    private ImageButton back;
    private ImageButton search;
    private ImageButton add;
    private EditText textv;
    private RecyclerView listv;
    private ArrayList<Object> doc_name,med_name,illness_name,date,totalcost,numpills;
    private medicaldatabase db=new medicaldatabase(medhistory.this);
    customadapter customadapter;
    private boolean isitempresent=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medhistory);
        add=(ImageButton)findViewById(R.id.add);               // it would be better if u made it floating like in whatsapp
        textv=(EditText)findViewById(R.id.textv);
        listv=(RecyclerView)findViewById(R.id.listv);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent additems=new Intent(medhistory.this,addhistory.class);
                startActivityForResult(additems,1);   // request code=1 over here..

            }
        });                          // the complete recycler view for viewing the items of the list should be here too..

         doc_name = new ArrayList<>();
         med_name = new ArrayList<>();
         illness_name = new ArrayList<>();
         date = new ArrayList<>();
         totalcost = new ArrayList<>();
         numpills = new ArrayList<>();
        getitemsfromdatabase();                      //this is for retrieving all details which were in the database..
        tot_items_list=tot_items_list+doc_name.size();
        if(isitempresent) {                          // already the items are added here we just have to add one more item the currently added one..
            customadapter = new customadapter(medhistory.this, doc_name, med_name, illness_name, date, totalcost, numpills);
            listv.setAdapter(customadapter);
            listv.post(new Runnable() {
                @Override
                public void run() {
                    listv.smoothScrollToPosition(tot_items_list-1);
                }
            });
            listv.setLayoutManager(new LinearLayoutManager(medhistory.this,LinearLayoutManager.VERTICAL,true));
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent pData) {        // the tough step is how do we determine whether it was returned as on updating or simply pressing back

        super.onActivityResult(requestCode, resultCode, pData);
        Cursor cursor=db.displaydetails();
        final int tot_items_db=cursor.getCount();
        ///////////////////////////////////////////////////////////////////////////
        if(requestCode==1 && tot_items_db==tot_items_list+1)
        {
            getlastitemfromdatabase();
            if(isitempresent) {
                customadapter = new customadapter(medhistory.this, doc_name, med_name, illness_name, date, totalcost, numpills);
                listv.setAdapter(customadapter);
                listv.post(new Runnable() {
                    @Override
                    public void run() {
                        listv.smoothScrollToPosition(tot_items_db-1);
                    }
                });
                listv.setLayoutManager(new LinearLayoutManager(medhistory.this,LinearLayoutManager.VERTICAL,true));
            }
        }
    }
    public void getitemsfromdatabase()
    {
        Cursor cursor=db.displaydetails();
        if(cursor.getCount()==0) {
            isitempresent=false;
        }
        else
        {
            isitempresent=true;
            while(cursor.moveToNext())
            {
                doc_name.add(cursor.getString(1));
                med_name.add(cursor.getString(2));
                illness_name.add(cursor.getString(3));
                date.add(cursor.getString(4));
                totalcost.add(String.valueOf(cursor.getInt(5)));
                numpills.add(String.valueOf(cursor.getInt(6)));
            }
        }
        cursor.close();
    }
    public void getlastitemfromdatabase()
    {
        Cursor cursor=db.displaydetails();
        if(cursor.getCount()==0) {
            isitempresent=false;
        }
        else {
            isitempresent = true;
            cursor.moveToLast();
            doc_name.add(cursor.getString(1));
            med_name.add(cursor.getString(2));
            illness_name.add(cursor.getString(3));
            date.add(cursor.getString(4));
            totalcost.add(String.valueOf(cursor.getInt(5)));
            numpills.add(String.valueOf(cursor.getInt(6)));
        }
     cursor.close();

    }
}