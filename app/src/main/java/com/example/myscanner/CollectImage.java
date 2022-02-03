package com.example.myscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollectImage extends AppCompatActivity{

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_image);

        imageView=findViewById(R.id.collectedImage);

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("Text");

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image").child(s);
imageView.setImageURI(Uri.parse(s));


     /*  databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()) {

                    if(snapshot1.getKey().equals("uri")) {
                        String s= snapshot1.getValue().toString();

                        Uri myUri = Uri.parse(s);
                        Log.i(myUri.toString(), "onDataChange: ");
                        imageView.setImageURI(myUri);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CollectImage.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }); */
    }
}