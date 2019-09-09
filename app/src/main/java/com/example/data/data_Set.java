package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class data_Set extends AppCompatActivity {
    DatabaseHelper db;
    private GridView gridView;
    private int user_id;
    Cursor cursor;
    TextView textView;

    private int j=0;
    Bitmap[] images;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__set);

        gridView = findViewById(R.id.gridView);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        user_id = (int) bundle.get("position");
        user_id+=1;


        cursor = db.getImage_user(user_id);


        if(cursor.getCount()==0){
            gridView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
        else{
            images = new Bitmap[cursor.getCount()];
            while(cursor.moveToNext()){
                byte[] img = cursor.getBlob(1);
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                images[j++] = bitmap;
            }
            gridView.setAdapter(new GridAdapter(getApplicationContext()));

        }

    }

    public class GridAdapter extends BaseAdapter{

        Context context;
        public GridAdapter(Context applicationContext) {
            this.context = applicationContext;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(500,500));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                imageView.setPadding(10, 15, 0, 15);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(images[position]);
            return imageView;

        }
    }
}
