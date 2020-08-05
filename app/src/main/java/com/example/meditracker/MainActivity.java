package com.example.meditracker;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends Activity {

    private final int coderegister=1;
    private final int codeupdate=2;
    private EditText a;
    private EditText write;
    private EditText view;
    private ImageView profilepic;
    private EditText namev;
    private Button notification;
    private Button trackerlist;
    private ImageView viewbi;
    Uri pic;
    String name,age,sex,height,weight,bloodgroup,imglocation;
    profiledatabase profile=new profiledatabase(MainActivity.this);
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button viewb = (Button) findViewById(R.id.viewb);
        Button editb = (Button) findViewById(R.id.editb);
        Button notification=(Button)findViewById(R.id.notification);
        Button trackerlist=(Button)findViewById(R.id.trackerlist);
        ImageView viewbi=(ImageView)findViewById(R.id.viewbi);
        namev=(EditText)findViewById(R.id.name);
         profilepic=(ImageView)findViewById(R.id.profilepic);
        profilepic.setImageBitmap(profile.getImage());
        ConstraintLayout layout=findViewById(R.id.layout);
        if(profile.displaydetailsofprofile()!=null)
        {
            viewb.setEnabled(true);
            viewb.setVisibility(View.VISIBLE);
            viewbi.setVisibility(View.VISIBLE);
        }
        if(profile.check()==false) {
            Intent start = new Intent(MainActivity.this, Registration.class);     // for registering so code=2;
            startActivityForResult(start, coderegister);
            if(profile.displaydetailsofprofile()!=null)
            {
               viewb.setEnabled(true);
               viewb.setVisibility(View.VISIBLE);
               viewbi.setVisibility(View.VISIBLE);
            }
        }
        namev.setText("WELCOME "+profile.getthename());
        editb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,Registration.class);
                startActivityForResult(i,codeupdate);
            }
        });
        viewb.setOnClickListener(new View.OnClickListener() {         // the view is not working because it has not received the items yet hence it has null reference..
            @Override                                                 // do it with cursor..
            public void onClick(View v) {                             // extract it directly from the database..
                Intent j=new Intent(MainActivity.this,viewprofile.class); /////here we go to the view profile class
                startActivity(j);
            }
        });

        trackerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(MainActivity.this,medhistory.class);
                startActivity(l);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(MainActivity.this,notificationlist.class);
                startActivity(k);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent pData) {
        super.onActivityResult(requestCode,resultCode,pData);
        if (requestCode == codeupdate) {                                 //////////this is for updating

            name=pData.getStringExtra("NAME");
            age=pData.getStringExtra("AGE");
            sex=pData.getStringExtra("SEX");
            height=pData.getStringExtra("HEIGHT");
            weight=pData.getStringExtra("WEIGHT");
            imglocation=pData.getStringExtra("IMGLOCATION");
            bloodgroup=pData.getStringExtra("BLOODGROUP");
            namev.setText("WELCOME "+name);
            Uri pictemp=pData.getData();
            if(pictemp==null)
            {
              bitmap=profile.getImage();
             profilepic.setImageBitmap(bitmap);
            }
            else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pictemp);
                    profilepic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            person p=new person(name,Integer.parseInt(age),sex,Double.parseDouble(height),Double.parseDouble(weight),bloodgroup);
            boolean success=profile.updatepersonaldetails(p,bitmap);
            if(success==true)
            {
                Toast.makeText(this, "Updated details", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Updating failed", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode==coderegister){                                            //////this is for registering
            name=pData.getStringExtra("NAME");
            age=pData.getStringExtra("AGE");
            sex=pData.getStringExtra("SEX");
            height=pData.getStringExtra("HEIGHT");
            weight=pData.getStringExtra("WEIGHT");
            bloodgroup=pData.getStringExtra("BLOODGROUP");
            imglocation=pData.getStringExtra("IMGLOCATION");
            namev.setText("WELCOME "+name);
             pic=pData.getData();
            try{
                 bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),pic);
                profilepic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            person p=new person(name,Integer.parseInt(age),sex,Double.parseDouble(height),Double.parseDouble(weight),bloodgroup);
            boolean success=profile.addpersonaldetails(p,bitmap);
            if(success==true)
            {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, imglocation, Toast.LENGTH_SHORT).show();
            }
        }
    }
}