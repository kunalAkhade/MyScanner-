package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getStorageDirectory;

public class Scan extends AppCompatActivity{
ImageView imageView;
Button extract,scan;
String s;
    private static final int PIC_CROP = 1;
    Bitmap selectedBitmap;

    Uri image_uri;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        imageView=findViewById(R.id.imageView);

    scan=findViewById(R.id.scan);
        image_uri=getIntent().getData();


 //imageView.setImageURI(image_uri);

       PerformCrop(image_uri);

       // imageView.setImageURI(image_uri);


        extract=findViewById(R.id.extract);
        scan=findViewById(R.id.scan);

        extract.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Convert to byte array
            /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();

                selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent in1 = new Intent(Scan.this, ExtractAct.class);
                in1.putExtra("image",byteArray);
                startActivity(in1); */

               /* Intent intent = new Intent(Scan.this, ExtractAct.class);

                intent.putExtra("BitmapImage", selectedBitmap);
                startActivity(intent); */


                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(selectedBitmap);
                FirebaseVision firebaseVision = FirebaseVision.getInstance();
                FirebaseVisionTextRecognizer firebaseVisionTextRecognizer =firebaseVision.getOnDeviceTextRecognizer();
                Task<FirebaseVisionText> task= firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
                task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>(){
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        s=firebaseVisionText.getText();
                        Intent intent = new Intent(Scan.this, ExtractAct.class);
                        intent.putExtra("message", s);
                        startActivity(intent);
                    }
                });
                task.addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Scan.this, "Failed", Toast.LENGTH_SHORT).show();
                        Log.i("102","failed");
                    }
                });



            }
        });

        scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                   Intent intent=new Intent(Scan.this,ScanRecyclerView.class);
                   intent.setData(image_uri);
                   startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

            //    selectedBitmap = cropImageView.getCroppedImage();

                imageView.setImageURI(resultUri);
                try {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);



                } catch (IOException e) {
                    e.printStackTrace();

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


       /* if (requestCode == PIC_CROP) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                 selectedBitmap = extras.getParcelable("data");

                imageView.setImageBitmap(selectedBitmap);

            }
        }*/
    }

   /* private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    } */

    private void PerformCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .setMultiTouchEnabled(true)
                .start(this);
    }




}