package com.example.myscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.util.ArrayList;

import static com.scanlibrary.ScanConstants.OPEN_INTENT_PREFERENCE;
import static com.scanlibrary.ScanConstants.OPEN_MEDIA;

public class ScanRecyclerView extends AppCompatActivity{

    RecyclerView recyclerView;
    Radapter radapter;
    ArrayList<Uri> imageViews;
    Button button;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_recycler_view);
        recyclerView=findViewById(R.id.recyle);
        Button button=findViewById(R.id.Gal);

        Uri uri=getIntent().getData();
        imageViews=new ArrayList<>();
        imageViews.add(i++,uri);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        radapter=new Radapter(this,imageViews);
        recyclerView.setAdapter(radapter);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int preference = OPEN_MEDIA;
                Intent intent = new Intent(ScanRecyclerView.this, ScanActivity.class);
                intent.putExtra(OPEN_INTENT_PREFERENCE, preference);
                startActivityForResult(intent, 122);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 122 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
          //  Bitmap bitmap = null;
            // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            // getContentResolver().delete(uri, null, null);
            // scannedImageView.setImageBitmap(bitmap);
            imageViews.add(i++,uri);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            radapter=new Radapter(this,imageViews);
            recyclerView.setAdapter(radapter);

        }
        else{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}