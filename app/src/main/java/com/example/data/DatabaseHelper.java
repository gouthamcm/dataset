package com.example.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private long ins;

    private static final String DATABASE_NAME = "dataset.db";

    private static final int DATABASE_VERSION = 1;

    private static final String table_user = "Create Table user (id integer primary key, name text, age text, gender text)";

    private static final String table_images = "Create Table user_images (id integer primary key autoincrement,image BLOB not null, user_id integer,FOREIGN KEY(user_id) references user(id) )";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Toast.makeText(context, "Database created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("TAG","inserting");
        db.execSQL(table_user);
        db.execSQL(table_images);
        Log.e("TAG", "Database created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("TAG","inside onUpgrade");
        db.execSQL("Drop table if exists user");
        db.execSQL("Drop table if exists user_images");
        onCreate(db);
    }

    public boolean insert_user(String name, String age, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        ins = db.insert("user",null,contentValues);
        Log.d("TAG","data inserted "+ins);
        return true;
    }

    public boolean insert_image(byte[] data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", data);
        contentValues.put("user_id", ins);
        long inser = db.insert("user_images",null,contentValues);
        Log.d("TAG","image data inserted");
        if(inser == -1) return false;
        else return true;
    }

    public Cursor getdata_user(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from user",null);
        return res;
    }

    public Cursor getImage_user(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from user_images",null);
        return res;
    }
//    public ArrayList<Bitmap> getImage_user(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from user_images where user_id = pos",null);
////    }
//    public List<data_Set> getData(){
//        data_Set product = null;
//        List<data_Set> productList = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM Product", null);
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            product = new data_Set(cursor.getInt(0), cursor.getBlob(1), cursor.getInt(2));
//            productList.add(product);
//            cursor.moveToNext();
//        }
//        return productList;
//    }


    public List<Data_set_class> getALL(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Data_set_class> data = new ArrayList<Data_set_class>();

        Cursor cursor = db.rawQuery("Select * from user_images",null);

        if(cursor.moveToFirst()){
            do{
                Data_set_class data_set = new Data_set_class();
                data_set.setId(cursor.getInt(0));
                data_set.setImage(cursor.getBlob(1));
                data_set.setUser_id(cursor.getInt(2));
                 data.add(data_set);
            }while(cursor.moveToNext());

        }
        return data;
    }
}
