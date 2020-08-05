package com.example.meditracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class notificationsdatabase extends SQLiteOpenHelper {

    private static final String dbname="NOTIFICATIONS.db";
    private static final int version=1;
    public notificationsdatabase(Context context) {
        super(context, dbname, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlcreate="CREATE TABLE NOTIFICATIONS(ID INTEGER PRIMARY KEY AUTOINCREMENT,MEDICINE_NAME TEXT,TIMESCOMBINED TEXT,CHANNEL INTEGER,DATE TEXT)";
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTIFICATIONS");
        onCreate(db);
    }
    public boolean addnotificationdetails(String mname,String tcombined,int channel_no,String date)  {                      // here p is an object of the medrecords class. which stores all info in a particular class.

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put("MEDICINE_NAME",mname);            //// THE MEDICINE NAME CORRESPONDING TO THE NOTIFICATIONS IS ALSO STORED.
        cv.put("TIMESCOMBINED",tcombined);      ////////SO OUR OBJECTIVE THAT IS THE STORAGE OF THE TIMES IS COMPLETED..
        cv.put("CHANNEL",channel_no);            ///// FOR EVERY SPECIFIC MEDICINE IT HAS A CHANNEL NAME SO IT IS EASIER TO CANCEL ALL ALARMS CORRESPONDING TO A PARTICULAR MEDICINE..
        cv.put("DATE",date);
        long insert=db.insert("NOTIFICATIONS", null, cv);
        if(insert==-1)
            return false;
        else
            return true;
    }
    public Cursor displaynotifdetails()
    {
        String query="SELECT * FROM NOTIFICATIONS";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null)
        {
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    public boolean updatenotificationdetails(String mname,String tcombined,int pos){
        SQLiteDatabase db=this.getWritableDatabase();
        String position=String.valueOf(pos);
        ContentValues cv=new ContentValues();
        cv.put("MEDICINE_NAME",mname);            //// THE MEDICINE NAME CORRESPONDING TO THE NOTIFICATIONS IS ALSO STORED.
        cv.put("TIMESCOMBINED",tcombined);
        long update=db.update("NOTIFICATIONS",cv,"ID = ?",new String[]{position});
        if(update==-1)
            return false;
        else
            return true;
    }
    public int countrows()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, "NOTIFICATIONS");
        db.close();
        return count;
    }
    public void updatealarmtext(int pos,String content){
        SQLiteDatabase db=this.getWritableDatabase();
        String position=String.valueOf(pos);
        ContentValues cv=new ContentValues();
        cv.put("TIMESCOMBINED",content);
        db.update("NOTIFICATIONS",cv,"ID = ?",new String[]{position});
        return;
    }
}
