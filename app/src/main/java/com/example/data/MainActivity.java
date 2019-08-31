package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogClass.DialogClassListener {
    MyCanvas myCanvas;
    Button submit;
    Button reset;
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
    }

    public void openDialog(){
        DialogClass dialogClass = new DialogClass();
        dialogClass.show(getSupportFragmentManager(),"example dialog");
    }
}
