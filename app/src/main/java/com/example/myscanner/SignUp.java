package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

import static android.widget.Toast.*;

public class SignUp extends AppCompatActivity{
   Button signup;
   EditText editText;
   EditText editText2;
   ProgressBar progressBar;
   private FirebaseAuth mAuth;
   Button button;

   FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
   DatabaseReference databaseReference=firebaseDatabase.getReference().child("Users");
    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup=findViewById(R.id.SignUpButton);
        editText=findViewById(R.id.UsernameSignUp);
        editText2=findViewById(R.id.passwordSignUp);
        progressBar=findViewById(R.id.progressBar);
        button=findViewById(R.id.button2);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        } */


        signup.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String username = editText.getText().toString();
            String password = editText2.getText().toString();

            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(SignUp.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("1", "createUserWithEmail:success");
                            String user = mAuth.getCurrentUser().getUid();
                            databaseReference.child(user).child("OCR").setValue("2");
                            databaseReference.child(user).child("PDF").setValue("3");

                            Intent intent =new Intent(SignUp.this,LoginPage.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.w("2", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });









        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this,LoginPage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
