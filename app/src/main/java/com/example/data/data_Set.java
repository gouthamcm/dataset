package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class data_Set extends AppCompatActivity {
    DatabaseHelper db;
    private GridView listView;
    private int user_id;

    TextView textView;

    ArrayList<Data_set_class> DATA = new ArrayList<Data_set_class>();
    Bitmap[] images;

    List<Data_set_class> data_set_class;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__set);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        user_id = (int) bundle.get("position");
        user_id+=1;
        db = new DatabaseHelper(this);
        data_set_class = db.getALL(user_id);

        listView.setVerticalSpacing(1);
        listView.setHorizontalSpacing(1);
        listView.setAdapter(new GridAdapter(getApplicationContext(),data_set_class));
//        cursor = db.getImage_user();
//
//
//        if(cursor.getCount()==0){
//            gridView.setVisibility(View.INVISIBLE);
//            textView.setVisibility(View.VISIBLE);
//        }
//        else{
//            //images = new Bitmap[cursor.getCount()];
//            while(cursor.moveToNext()){
//                byte[] img = cursor.getBlob(tamil);
//                long user_id1 = cursor.getInt(2);
//                long id = cursor.getInt(0);
//                if(user_id1 == user_id){
//
//                }
//                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
//                //   images[j++] = bitmap;
//            }
//            gridView.setAdapter(new GridAdapter(getApplicationContext()));
//
//        }

    }

    public class GridAdapter extends BaseAdapter{

        Context context;
        private List<Data_set_class> DATASET;
        public GridAdapter(Context applicationContext, List<Data_set_class> mdata) {

            this.context = applicationContext;
            this.DATASET = mdata;
        }

        @Override
        public int getCount() {
            return DATASET.size();
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

                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels/2;
                int height = dm.heightPixels/4;
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(width,height));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 0, 0, 0);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            Data_set_class picture = DATASET.get(position);
//            if(picture.getUser_id() == user_id){
//                byte[] outImage=picture.getImage();
//                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
//                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//                imageView.setImageBitmap(theImage);
//            }
//            else{
//                Toast.makeText(context, "nothing to display", Toast.LENGTH_SHORT).show();
//            }
//
            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
            return imageView;

        }
    }
}
