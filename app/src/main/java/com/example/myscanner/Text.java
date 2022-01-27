package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Text extends AppCompatActivity{
    TextView textView;
 FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
 DatabaseReference databaseReference=firebaseDatabase.getReference();

 Button copyText, Del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
textView=findViewById(R.id.textView6);
copyText=findViewById(R.id.copy);
Del=findViewById(R.id.del);
        Bundle bundle = getIntent().getExtras();
        String string = bundle.getString("Text");
        Log.i("hi",string);

       databaseReference= databaseReference.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("OCR");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

               for(DataSnapshot snapshot1:snapshot.getChildren()) {

                   if(snapshot1.getKey().equals(string)) {
                       String text=snapshot1.getValue().toString();
                       textView.setText(text);
                   }
               }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Text.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String getstring = textView.getText().toString();

                ClipData clip = ClipData.newPlainText("Text", getstring);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_LONG).show();
            }
        });

      Del.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view) {
              databaseReference.child(string).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                          textView.setText("Nothing to show");
                          Intent intent=new Intent(Text.this,HomeActivity.class);
                          startActivity(intent);
                      }
                      else{
                          Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG);
                      }

                  }
              });
          }
      });

    }


}