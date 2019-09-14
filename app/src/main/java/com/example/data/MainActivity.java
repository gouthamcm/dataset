package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements DialogClass.DialogClassListener {
    MyCanvas myCanvas;
    Button submit;
    DialogClass dialogClass;
    Button reset;
    ImageView imageView;
    private int count=1;
    Integer[] images_array = {R.drawable.tamil1,R.drawable.tamil2,R.drawable.tamil3,R.drawable.tamil4,R.drawable.tamil5,
            R.drawable.tamil6,R.drawable.tamil7,R.drawable.tamil8,R.drawable.tamil9,R.drawable.tamil10,
            R.drawable.tamil11,R.drawable.tamil12,R.drawable.tamil13,R.drawable.tamil14,R.drawable.tamil15,
            R.drawable.tamil16,R.drawable.tamil17,R.drawable.tamil18,R.drawable.tamil19,R.drawable.tamil20,
            R.drawable.tamil21,R.drawable.tamil22,R.drawable.tamil23,R.drawable.tamil24,R.drawable.tamil25,
            R.drawable.tamil26,R.drawable.tamil27,R.drawable.tamil28,R.drawable.tamil29,R.drawable.tamil30,
            R.drawable.tamil31,R.drawable.tamil32,R.drawable.tamil33,R.drawable.tamil34,R.drawable.tamil35,
                                };

    boolean doubleBackToExitPressedOnce = false;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count=0;
        myCanvas = findViewById(R.id.canvas);
        myCanvas.setBackgroundColor(Color.BLACK);
        submit = findViewById(R.id.subimt);
        reset = findViewById(R.id.reset);
        imageView = findViewById(R.id.imageView);
        if(count==0) imageView.setImageDrawable(getResources().getDrawable(images_array[0]));
        db = new DatabaseHelper(this);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.clear();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] data = getBitmapAsByteArray(myCanvas.mBitmap);
                db.insert_image(data);
                myCanvas.clear();
                Toast.makeText(MainActivity.this,"Data submitted successfully",Toast.LENGTH_LONG).show();
                imageView.setImageDrawable(getResources().getDrawable(images_array[(count+1)%35 ]));
                count+=1;
            }
        });

        openDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.recorded_data:
                Intent intent = new Intent(this,Recorded_Data.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyTexts(String Username, String Userage, String Usergender) {
        Toast.makeText(this, Username+" "+Userage+" "+Usergender , Toast.LENGTH_SHORT).show();
        Log.e("TAG","entering values");
        db.insert_user(Username,Userage,Usergender);
        dialogClass.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void openDialog(){
        dialogClass = new DialogClass();
        dialogClass.setCancelable(false);
        dialogClass.show(getSupportFragmentManager(),"example dialog");
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
