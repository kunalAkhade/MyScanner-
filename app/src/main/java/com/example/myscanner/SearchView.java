package com.example.myscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchView extends AppCompatActivity implements SearchViewAdapter.onNoteListener{

    RecyclerView recyclerView;
    //DatabaseReference databaseReference;
    SearchViewAdapter adapter;
   // ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);


        recyclerView=findViewById(R.id.rv);
     //   databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       // list=new ArrayList<>();



        FirebaseRecyclerOptions<model> options=new FirebaseRecyclerOptions.Builder<model>().setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image"),model.class).build();

        adapter=new SearchViewAdapter(options,this,this);
        recyclerView.setAdapter(adapter);







    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item =menu.findItem(R.id.search);
        android.widget.SearchView searchView= (android.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

   private void processsearch(String s){
        FirebaseRecyclerOptions<model> options= new FirebaseRecyclerOptions.Builder<model>().setQuery(FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image").orderByChild("ExtractedText").startAt(s).endAt(s+"\uf8ff"),model.class).build();
        adapter= new SearchViewAdapter(options,this,this);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNote(int position, String text) {
        Intent intent=new Intent(this,CollectImage.class);
        Log.i(text, "onNote: ");
        intent.putExtra("Text",text);
        startActivity(intent);
    }
}