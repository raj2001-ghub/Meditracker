package com.example.meditracker;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;

public class profiledatabase extends SQLiteOpenHelper{

    private static final String dbname="profile.db";
    private static final int version=1;
    public profiledatabase(Context context)
    {
        super(context,dbname,null,version);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {       //in the create statement add a variable for image type which is blob
        String sqlcreate="CREATE TABLE PROFILE(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AGE INTEGER,SEX TEXT,HEIGHT REAL,WEIGHT REAL,BLOOD_GROUP TEXT,IMG blob not null)";
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PROFILE");
        onCreate(db);
    }
    public boolean updatepersonaldetails(person p, Bitmap b)  {                  // the only thing left for us to do here is that we have to take another arguement  in here for image file..
        SQLiteDatabase db=this.getWritableDatabase();

            ContentValues cv=new ContentValues();
            cv.put("NAME",p.getNamep());
            cv.put("AGE",p.getAgep());
            cv.put("SEX",p.getSexp());
            cv.put("HEIGHT",p.getHeightp());
            cv.put("WEIGHT",p.getWeightp());
            cv.put("BLOOD_GROUP",p.getBloodgroupp());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imgbyte = stream.toByteArray();
            cv.put("IMG", imgbyte);
            long insert=db.update("PROFILE",cv,"ID = ?",new String[]{"1"});

            if (insert==-1)
                return false;
                else
                return true;



    }
    public boolean addpersonaldetails(person p,Bitmap b)  {

        SQLiteDatabase db=this.getWritableDatabase();

            ContentValues cv=new ContentValues();
            cv.put("NAME",p.getNamep());
            cv.put("AGE",p.getAgep());
            cv.put("SEX",p.getSexp());
            cv.put("HEIGHT",p.getHeightp());
            cv.put("WEIGHT",p.getWeightp());
            cv.put("BLOOD_GROUP",p.getBloodgroupp());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] imgbyte = stream.toByteArray();
            cv.put("IMG", imgbyte);
            long insert=db.insert("PROFILE", null, cv);
            if(insert==-1)
                return false;
            else
                return true;

    }
    public person display(){                // name of the  function is display function to display all the items..
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM PROFILE";
        person p_temp = null;
        Cursor cursor;
        cursor = db.rawQuery(query,null);
        System.out.println("hey man");
        if(cursor.moveToFirst())
        {
            String name=cursor.getString(1);
            int age=cursor.getInt(2);
            String sex=cursor.getString(3);
            double height=cursor.getDouble(4);
            double weight=cursor.getDouble(5);
            String bloodgroup=cursor.getString(6);
            p_temp=new person(name,age,sex,height,weight,bloodgroup);

        }
        db.close();
        cursor.close();
        return p_temp;
    }
    public String getthename(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM PROFILE";
        String name = null;
        Cursor cursor;
        cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            name = cursor.getString(1);
        }
        db.close();
        cursor.close();
        return name;
    }
    public boolean check(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM PROFILE";
        Cursor cursor;
        cursor = db.rawQuery(query,null);
        if(!cursor.moveToFirst())
        {
            return false;
        }
        else
            return true;
    }
    public Bitmap getImage()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM PROFILE";
        Cursor cursor;
        Bitmap bt=null;
        cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            byte[] img=cursor.getBlob(7);
            bt= BitmapFactory.decodeByteArray(img,0,img.length);
        }
        return bt;
    }
    public Cursor displaydetailsofprofile(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM PROFILE";
        Cursor cursor;
        cursor = db.rawQuery(query,null);
        return cursor;
    }
}
