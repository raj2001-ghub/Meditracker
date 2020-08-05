package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class viewprofile extends ListActivity {

    ArrayAdapter<String> ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        profiledatabase db=new profiledatabase(viewprofile.this);
        person a=db.display();
        if(a==null)
            System.out.println("hi");
        String items[]={"NAME - "+a.getNamep(),"AGE - "+Integer.toString(a.getAgep()),"SEX - "+a.getSexp(),"HEIGHT - "+Double.toString(a.getHeightp()),"WEIGHT - "+Double.toString(a.getWeightp()),
                "BLOODGROUP - "+a.getBloodgroupp()};
        ab=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                items){
            @Override

            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.RED);
                text.setTextSize(23);
                text.setPadding(30,10,0,0);
                text.setCursorVisible(false);
                text.setHeight(200);
                return view;
            }
        };
        setListAdapter(ab);
    }
}