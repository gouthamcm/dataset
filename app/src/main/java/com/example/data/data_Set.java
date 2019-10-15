package com.example.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private String user_name, user_age, user_gender;
    TextView textView;
    int count=0;
    BitmapDrawable drawable;
    ImageView im;

    GridAdapter gridAdapter;
    ArrayList<Data_set_class> DATA = new ArrayList<Data_set_class>();
    Bitmap[] images;
    int i=0;

    ArrayList<File> fileList;

    private int COUNT_OF = 0;

    int j=0;
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
        user_name = (String) bundle.get("name");
        user_age = (String) bundle.get("age");
        user_gender = (String) bundle.get("gender");

        Log.e("name",user_name+user_age+user_gender);
        user_id+=1;
        db = new DatabaseHelper(this);
        data_set_class = db.getALL(user_id);

        listView.setVerticalSpacing(1);
        listView.setHorizontalSpacing(1);
        gridAdapter = new GridAdapter(getApplicationContext(),data_set_class);
        listView.setAdapter(gridAdapter);
        //Log.d("TAG","COMPLETED");
        i=0;
        fileList = new ArrayList<>();
        //check_permission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_images,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.save:
                if(isPermissionGranted()){
                    save_to_sd();
                    COUNT_OF++;
                }
                break;
            case R.id.share:
                if(isPermissionGranted()){
                    save_to_sd();
                    share();
                }
                break;



        }
        return super.onOptionsItemSelected(item);
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

            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);




            return imageView;
        }
    }



    public void save_to_sd(){
        Log.d("TAG","COMPLETED");
        //Toast.makeText(this, String.valueOf(gridAdapter.getCount()), Toast.LENGTH_SHORT).show();

        fileList = new ArrayList<>();
        ArrayList<Uri> uri = new ArrayList<>();
        for(j=0;j<gridAdapter.getCount();j++){

            Data_set_class picture = data_set_class.get(j);

            byte[] outImage=picture.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap bm = BitmapFactory.decodeStream(imageStream);


            String filename = "tamil_" + String.valueOf(j) + ".png";

            File sd = Environment.getExternalStorageDirectory();
            File folder = new File(sd + "/"+user_name+"-"+user_age+"-"+user_gender+"/");
            folder.mkdir();

            File dest = new File(folder, filename);

            try {
                FileOutputStream out;
                out = new FileOutputStream(dest);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                fileList.add(dest);
                Log.e("TAG","DONE");
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.e("TAG","file not found exeception");
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("TAG","IOEXECEPTION");
                e.printStackTrace();
            }

            Log.e("TAG",dest.toString());
            //Toast.makeText(getApplicationContext(), "image saved at" + dest.toString(), Toast.LENGTH_SHORT).show();
            //MediaStore.Images.Media.insertImage(getContentResolver(), bm, user_name, user_age);
            Log.d("TAG",dest.toString());



        }



    }

    public void share(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
        intent.setType("image/*"); /* This example is sharing jpeg images. */

        ArrayList<Uri> files = new ArrayList<Uri>();

        for(File path : fileList /* List of the files you want to send */) {
            File file = new File(path.toString());
            Uri photoURI = FileProvider.getUriForFile(data_Set.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    path);
            //Uri uri1 = Uri.fromFile(file);
            files.add(photoURI);
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"purushothsankari@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Tamil Script");
        intent.putExtra(Intent.EXTRA_TEXT, "Name: "+user_name + "\n" + "Age: " + user_age + "\n"
                        + "Gender: " + user_gender);
        startActivity(intent);
    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }

        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    save_to_sd();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
