package com.example.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class data_Set extends AppCompatActivity {
    DatabaseHelper db;
    private GridView listView;
    private int user_id;
    private String user_name;
    TextView textView;

    ArrayList<Data_set_class> DATA = new ArrayList<Data_set_class>();
    Bitmap[] images;
    int i=0;

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
        //user_name = (String) bundle.get("name");

        user_id+=1;
        db = new DatabaseHelper(this);
        data_set_class = db.getALL(user_id);

        listView.setVerticalSpacing(1);
        listView.setHorizontalSpacing(1);
        listView.setAdapter(new GridAdapter(getApplicationContext(),data_set_class));


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_images,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch(id){
//            case R.id.share:
//                //share();
//                Toast.makeText(this, "sharing", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.save:
//                //saveToSd();
//                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }



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

            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);


            String filename = "image_" + String.valueOf(i++) + ".png";

            File sd = Environment.getExternalStorageDirectory();
            File folder = new File(sd + "/WALLPAPER");
            folder.mkdir();

            File dest = new File(folder, filename);
            try {
                FileOutputStream out;
                out = new FileOutputStream(dest);
                theImage.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //Toast.makeText(context, "image saved at" + dest.toString(), Toast.LENGTH_SHORT).show();
            return imageView;
        }
    }

    public void saveToSd(){
        int pos = 0;

        while(pos<data_set_class.size()){
            Data_set_class picture = data_set_class.get(pos);
            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            String filename = "Image_"+String.valueOf(pos+1)+".png";
            File sd = Environment.getExternalStorageDirectory();
            File folder = new File(sd + "/" + user_name);
            folder.mkdir();

            File dest = new File(folder, filename);
            try {
                FileOutputStream out;
                out = new FileOutputStream(dest);
                theImage.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pos++;
        }

    }

    public void share(){
        Intent shareIntent = new Intent();

        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);

        ArrayList<Uri> files = new ArrayList<Uri>();
        int pos = 0;

        while(pos<data_set_class.size()){
            Data_set_class picture = data_set_class.get(pos);
            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            //files.add(  )
            pos++;
        }
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
    }
}
