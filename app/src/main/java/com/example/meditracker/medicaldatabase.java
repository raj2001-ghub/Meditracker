package com.example.meditracker;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class medicaldatabase extends SQLiteOpenHelper{
    private static final String dbname="TRACKLIST.db";
    private static final int version=1;
    public medicaldatabase(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   // HERE WE WRITE THE TABLE NAME..
        String sqlcreate="CREATE TABLE TRACKLIST(ID INTEGER PRIMARY KEY AUTOINCREMENT,DOCTORNAME TEXT,MEDICINENAME TEXT,ILLNESSNAME TEXT,DATEOFBUYING TEXT,TOTALPRICE INTEGER,NUMBEROFPILLS INTEGER)";
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TRACKLIST");
        onCreate(db);
    }
    public boolean addmedicinedetails(medrecords p)  {                      // here p is an object of the medrecords class. which stores all info in a particular class.

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("DOCTORNAME",p.getAns1d());
        cv.put("MEDICINENAME",p.getAns2d());
        cv.put("ILLNESSNAME",p.getAns3d());
        cv.put("DATEOFBUYING",p.getAns4d());
        cv.put("TOTALPRICE",p.getAns5d());
        cv.put("NUMBEROFPILLS",p.getAns6d());
        long insert=db.insert("TRACKLIST", null, cv);
        if(insert==-1)
            return false;
        else
            return true;

    }
    public Cursor displaydetails()
    {
        String query="SELECT * FROM TRACKLIST";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

}
