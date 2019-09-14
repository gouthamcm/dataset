package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recorded_Data extends AppCompatActivity {

    private DatabaseHelper db;
    private ListView listView;
    Cursor cursor;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded__data);
        listView = findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<>();
        db = new DatabaseHelper(this);
        cursor = db.getdata_user();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data to be shown", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                list.add(cursor.getString(1));
                ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Recorded_Data.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recorded_Data.this,data_Set.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }


//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
}
