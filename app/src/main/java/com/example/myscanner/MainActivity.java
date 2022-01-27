package com.example.myscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
     Animation topAnim, botanim;
     ImageView imageView;
     TextView textView;
     ImageView imageView2;
     ImageView imageView3;
    @Override
    @SuppressWarnings("DEPRECATION")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        topAnim= AnimationUtils.loadAnimation(this, R.anim.anim_top);
        botanim=AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        imageView=findViewById(R.id.imageView);
        textView=findViewById(R.id.MyScanner);
        imageView2=findViewById(R.id.imageView4);
         imageView3=findViewById(R.id.imageView3);
        imageView.setAnimation(topAnim);
        textView.setAnimation(botanim);
        imageView2.setAnimation(botanim);
        imageView3.setAnimation(topAnim);

       new Handler().postDelayed(new Runnable(){
           @Override
           public void run() {
               Intent i=new Intent(MainActivity.this, SignUp.class);
               startActivity(i);
           }
       }, 3000);

    }
}