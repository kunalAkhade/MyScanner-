package com.example.myscanner;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Radapter extends RecyclerView.Adapter<Radapter.MyViewHolder>{

    Context context;
    ArrayList<Uri> imageViews;
    public Radapter(Context context,ArrayList imageViews) {
        this.context=context;
        this.imageViews=imageViews;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.image,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Radapter.MyViewHolder holder, int position) {
        holder.imageView.setImageURI(imageViews.get(position));
    }


    @Override
    public int getItemCount() {
        return imageViews.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.pdfImage);

        }


    }




}
