package com.example.meditracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Registration extends Activity {

    private EditText name;
    private EditText sex;
    private EditText age;
    private EditText height;
    private EditText weight;
    private EditText bloodgroup;
    private Button pic;
    private Button register;
    private TextView disclaimer;
    private ImageView img;
    private String x;
    private Uri pickedImage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=(EditText)findViewById(R.id.name);
        age=(EditText)findViewById(R.id.age);
        sex=(EditText)findViewById(R.id.sex);
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        bloodgroup=(EditText)findViewById(R.id.bloodgroup);
        pic=(Button)findViewById(R.id.pic);
        register=(Button)findViewById(R.id.register);
        img=(ImageView)findViewById(R.id.imageView);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        disclaimer.setText(" * Please click the Register/Update button to save your details    and to go back to previous page");
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 12345);
            }
        });

        profiledatabase profile=new profiledatabase(Registration.this);
        if(profile.check()==false) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                         //// for registering of the details..
                    Intent i = new Intent();                               //in both cases sending the image location is very necessary..
                    String a = name.getText().toString();
                    String b = age.getText().toString();
                    String c = sex.getText().toString();
                    String d = height.getText().toString();
                    String e = weight.getText().toString();
                    String f = bloodgroup.getText().toString();
                    i.putExtra("NAME", a);
                    i.putExtra("AGE", b);
                    i.putExtra("SEX", c);
                    i.putExtra("HEIGHT", d);
                    i.putExtra("WEIGHT", e);
                    i.putExtra("BLOODGROUP", f);
                    i.setData(pickedImage);
                    i.putExtra("IMGLOCATION",x);
                    setResult(2, i);
                    finish();
                }
            });
        }
        else{
            register.setText("UPDATE");          // for updating the details.
            person item_list=profile.display();
            final String a_temp=item_list.getNamep();
            final String b_temp=String.valueOf(item_list.getAgep());
            final String c_temp=item_list.getSexp();
            final String d_temp=String.valueOf(item_list.getHeightp());
            final String e_temp=String.valueOf(item_list.getWeightp());
            final String f_temp=String.valueOf(item_list.getBloodgroupp());
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent();
                    String a=name.getText().toString(); // but here we have to add one attribute that is we have to add that property that only those things should be changed which are overridden
                    String b=age.getText().toString();
                    String c=sex.getText().toString();
                    String d=height.getText().toString();
                    String e=weight.getText().toString();
                    String f=bloodgroup.getText().toString();
                    String a_space=a.replaceAll("\\s","");
                    String b_space=b.replaceAll("\\s","");
                    String c_space=c.replaceAll("\\s","");
                    String d_space=d.replaceAll("\\s","");
                    String e_space=e.replaceAll("\\s","");
                    String f_space=f.replaceAll("\\s","");
                    if(a_space==null|| TextUtils.isEmpty(a_space))
                    i.putExtra("NAME",a_temp);
                    else
                        i.putExtra("NAME",a);
                    if(b_space==null|| TextUtils.isEmpty(b_space))
                        i.putExtra("AGE",b_temp);
                    else
                        i.putExtra("AGE",b);
                    if(c_space==null|| TextUtils.isEmpty(c_space))
                        i.putExtra("SEX",c_temp);
                    else
                        i.putExtra("SEX",c);
                    if(d_space==null|| TextUtils.isEmpty(d_space))
                        i.putExtra("HEIGHT",d_temp);
                    else
                        i.putExtra("HEIGHT",d);
                    if(e_space==null|| TextUtils.isEmpty(e_space))
                        i.putExtra("WEIGHT",e_temp);
                    else
                        i.putExtra("WEIGHT",e);
                    if(f_space==null|| TextUtils.isEmpty(f_space))
                        i.putExtra("BLOODGROUP",f_temp);
                    else
                        i.putExtra("BLOODGROUP",f);
                    i.setData(pickedImage);
                    i.putExtra("IMGLOCATION",x);
                    setResult(1,i);
                    finish();
                }
            });
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == 12345 && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI

            pickedImage = data.getData();
            try{
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),pickedImage);
                img.setImageBitmap(bitmap);
                x=getpath(pickedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // At the end remember to close the cursor or you will end with the RuntimeException!
        }
    }
    public String getpath(Uri uri){
        if(uri==null)return null;
        return uri.getPath();
    }
}