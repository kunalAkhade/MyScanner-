package com.example.myscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class Scan2 extends AppCompatActivity{

    Uri uri;
    ImageView imageView;

    Button scan2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan2);
        imageView=findViewById(R.id.imageView2);
         scan2=findViewById(R.id.scan2);
        uri=getIntent().getData();

        imageView.setImageURI(uri);

        scan2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String targetPdf = "/sdcard/test.pdf";
                File filePath = new File(targetPdf);
            }
        });


    }
}