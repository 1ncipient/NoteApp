/**
 * Note activity; This the activity that shows when the user presses a note in the All Notes section.
 */
package com.example.henry.henrysosummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import com.example.henry.henrysosummative.dataModel.Note;

/**
 * This activity defines the note text entry. It extends from AppCompatActivity.
 */
public class NoteActivity extends AppCompatActivity {
    private static final String TAG = "NoteActivity";
    private TextView entryText;
    private Note note;

    /**
     * onCreate method. runs on startup.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.layout_note_activity);
        entryText= findViewById(R.id.noteTextLabel);
        //a new note is created given the position of the note in the arraylist. Its variables are already set.
        note = getIntent().getParcelableExtra("entry");
        getSupportActionBar().setTitle(note.getTitle());
        //sets the blank textview to its contents so that when you press the note the contents are supposed to show.
        entryText.setText(note.getEntry());

    }



}
