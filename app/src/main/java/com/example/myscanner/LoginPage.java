package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity{
Button button;
Button button2;
EditText editText3;
EditText editText4;
ProgressBar progressBar;
FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
DatabaseReference databaseReference=firebaseDatabase.getReference();

    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);

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


        button2=findViewById(R.id.LoginButton);
        editText3=findViewById(R.id.UsernameLogin);
        editText4=findViewById(R.id.passwordLogin);
        progressBar=findViewById(R.id.progressBar2);
     button2.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View view) {
             progressBar.setVisibility(View.VISIBLE);
                String email=editText3.getText().toString();
                String password=editText4.getText().toString();
             FirebaseAuth mAuth=FirebaseAuth.getInstance();

             mAuth.signInWithEmailAndPassword(email, password)
                     .addOnCompleteListener(LoginPage.this, task -> {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             Log.d("5", "signInWithEmail:success");
                             progressBar.setVisibility(View.INVISIBLE);
                             String user = mAuth.getCurrentUser().getUid();
                             Intent intent =new Intent(LoginPage.this,HomeActivity.class);
                             startActivity(intent);

                         } else {
                             // If sign in fails, display a message to the user.
                             progressBar.setVisibility(View.INVISIBLE);
                             Log.w("7", "signInWithEmail:failure", task.getException());
                             Toast.makeText(LoginPage.this, "Authentication failed.",
                                     Toast.LENGTH_SHORT).show();

                         }
                     });

         }
     });







    }


}