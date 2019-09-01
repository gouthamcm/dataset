package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogClass.DialogClassListener {
    MyCanvas myCanvas;
    Button submit;
    DialogClass dialogClass;
    Button reset;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myCanvas = findViewById(R.id.canvas);
        myCanvas.setBackgroundColor(Color.BLACK);
        submit = findViewById(R.id.subimt);
        reset = findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvas.clear();
            }
        });

        openDialog();
    }

    @Override
    public void applyTexts(String Username, String Userage, String Usergender) {
        Toast.makeText(this, Username+" "+Userage+" "+Usergender , Toast.LENGTH_SHORT).show();
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
}
