/**
 * This is the activity of notes for the favorited notes. Currently not supported. Will be supported in the next version.
 */
package com.example.henry.henrysosummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.henry.henrysosummative.adapter.EntryRecyclerAdapter;
import com.example.henry.henrysosummative.dataModel.Note;

import java.util.ArrayList;

public class FavoriteNotesActivity extends AppCompatActivity implements EntryRecyclerAdapter.OnNoteListener {

    private RecyclerView recyclerView;
    private ArrayList<Note> favoriteNotes = new ArrayList<>();
    private EntryRecyclerAdapter entryRecyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.layout_all_notes);

        getSupportActionBar().setTitle ("Favorite Notes");

        recyclerView= findViewById(R.id.recyclerView);
        initRecyclerView();


        entryRecyclerAdapter.notifyDataSetChanged();

    }



    private void initRecyclerView () {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        entryRecyclerAdapter = new EntryRecyclerAdapter(favoriteNotes, this);
        recyclerView.setAdapter(entryRecyclerAdapter);

    }

    @Override
    public void onEntryClick(int position) {
        Intent intent = new Intent (this, NoteActivity.class);
        startActivity (intent);
    }
}
