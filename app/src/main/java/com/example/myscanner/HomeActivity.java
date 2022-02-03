package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStorageState;
import static com.scanlibrary.ScanConstants.*;

public class HomeActivity extends AppCompatActivity{


    Uri imageUri,resultUri;
   Bitmap bitmap;
   Button button3;

   Button button4;
   Button button;
    int REQUEST_CODE = 99;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button=findViewById(R.id.SavedPhoto);

button3=findViewById(R.id.button3);

button4=findViewById(R.id.button4);

button3.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View view) {
        Intent intent= new Intent( HomeActivity.this, ExtractText.class);
        startActivity(intent);
    }
});

button.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View view) {
        Intent intent =new Intent(HomeActivity.this,SearchView.class);
        startActivity(intent);
    }
});

button4.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View view) {

        int preference = OPEN_MEDIA;
        Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
        intent.putExtra(OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }
});



    }

    public void check(View v){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                String[] permissions={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,1000);
            }
            else{
                OpenCamera();

            }
        }
        else{
          OpenCamera();

        }
    }

    private void OpenCamera(){
        ContentValues values= new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
       startActivityForResult(cameraIntent,1888);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
               // getContentResolver().delete(uri, null, null);
                // scannedImageView.setImageBitmap(bitmap);
                Log.i("Worked","suc");
                Intent i = new Intent(HomeActivity.this, ScanRecyclerView.class);
                i.setData(uri);
                startActivity(i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     if(requestCode==1888 && resultCode==Activity.RESULT_OK) {

         Intent i = new Intent(this, Scan.class);
         i.setData(imageUri);
         startActivity(i);
     }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i=new Intent(HomeActivity.this,SignUp.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }


}