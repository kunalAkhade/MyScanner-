package com.example.myscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

public class myAdapter extends Adapter<myAdapter.MyViewHolder>{

   Context context;
   ArrayList<String> list;
   onNoteListener NoteListener;
    public myAdapter(Context context,ArrayList list, onNoteListener NoteListener) {
        this.context=context;
        this.list=list;
        this.NoteListener=NoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v,NoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        onNoteListener NoteListener;
        public MyViewHolder(@NonNull View itemView,onNoteListener NoteListener) {
            super(itemView);
            textView=itemView.findViewById(R.id.items);
            this.NoteListener=NoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
         String text=textView.getText().toString();
           NoteListener.onNote(getAdapterPosition(),text);
        }
    }

  public interface onNoteListener{
        void onNote(int position,String text);
  }


}
