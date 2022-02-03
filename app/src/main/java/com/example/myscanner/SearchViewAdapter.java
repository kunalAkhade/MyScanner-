package com.example.myscanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static com.example.myscanner.R.layout.element;
import static com.example.myscanner.SearchViewAdapter.*;

public class SearchViewAdapter extends FirebaseRecyclerAdapter<model, MyViewHolder>{

      FirebaseRecyclerOptions<model> options;
      onNoteListener onNoteListener;
      Context context;
    public SearchViewAdapter(FirebaseRecyclerOptions<model> options, onNoteListener onNoteListener, Context context) {
        super(options);
        this.onNoteListener=onNoteListener;
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull model model) {
     //  myViewHolder.textView.setText(model.getExtractedText());
        myViewHolder.textView2.setText(model.getUri());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(element,parent,false);
        return new MyViewHolder(view,onNoteListener);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       // TextView textView;
        TextView textView2;
        onNoteListener NoteListener;
        public MyViewHolder(@NonNull View itemView, onNoteListener NoteListener) {
            super(itemView);
           // textView=itemView.findViewById(R.id.textView7);
            textView2=itemView.findViewById(R.id.textView7);
            this.NoteListener=NoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String text=textView2.getText().toString();
            Log.i(text, "onClick: ");
            NoteListener.onNote(getAdapterPosition(),text);
        }
    }

    public interface onNoteListener{
        void onNote(int position,String text);
    }



}




