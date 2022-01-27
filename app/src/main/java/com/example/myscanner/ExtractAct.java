package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class ExtractAct extends AppCompatActivity{
 TextView textView;
 Button button;
 EditText editText;
DatabaseReference firebaseDatabaseRef=FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);
textView=findViewById(R.id.textView2);
editText=findViewById(R.id.ET);
button=findViewById(R.id.button);
      /*  byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); */

       /* Intent intent = getIntent();

        Bitmap bmp = (Bitmap) intent.getParcelableExtra("BitmapImage"); */

       /* FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bmp);
        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer =firebaseVision.getOnDeviceTextRecognizer();
        Task<FirebaseVisionText> task= firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>(){
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String s=firebaseVisionText.getText();
                textView.setText(s);
            }
        });
        task.addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ExtractAct.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }); */

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        textView.setText(message);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth=FirebaseAuth.getInstance();
               String user= mAuth.getCurrentUser().getUid();
                String fileName=editText.getText().toString();
                String bestBefore = textView.getText().toString();
              firebaseDatabaseRef.child(user).child("OCR").child(fileName).setValue(bestBefore);

              Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
            }

        });
    }
}