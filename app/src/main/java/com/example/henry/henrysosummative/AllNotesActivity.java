/**
 * This java file is the Activity file that stores allNotes. This activity is called when the user presses
 * All Notes in the navigation menu.
 */

package com.example.henry.henrysosummative;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.henry.henrysosummative.adapter.EntryRecyclerAdapter;

import com.example.henry.henrysosummative.dataModel.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is the Activity for the All Notes section of the navigation menu.
 */
public class AllNotesActivity extends AppCompatActivity implements EntryRecyclerAdapter.OnNoteListener {

    public static final String LOG_TAG = "myLogs";

    //UI Components
    private RecyclerView recyclerView;
    private ArrayList <Note> allNotes = new ArrayList<>();
    private EntryRecyclerAdapter entryRecyclerAdapter;

    private String strDate;
    private static final String TAG = "AllNotesActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.layout_all_notes);
        //Toolbar title is set to All Notes
        getSupportActionBar().setTitle ("All Notes");

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(currentDate);

        recyclerView= findViewById(R.id.recyclerView);
        initRecyclerView();

        notesDirectory();
        entryRecyclerAdapter.notifyDataSetChanged();

    }


    /**
     * This method sets up the recyclerViewAdapter and the layoutManager
     */
    private void initRecyclerView () {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        entryRecyclerAdapter = new EntryRecyclerAdapter(allNotes, this);
        recyclerView.setAdapter(entryRecyclerAdapter);

    }

    /**
     * This method keeps adding a note based on the size of the array. Checks the directory, opens the file and creates a new note
     * with the following parameters; file name, file contents, and date.
     */
    private void notesDirectory() {
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        String theFile;
        for (int x = 1; x <=files.length; x++){
            theFile = "Note" + x + ".txt";
            Note note = new Note(theFile, openNote(theFile), strDate);
            allNotes.add(note);
        }
    }

    /**
     * This method opens the notes given the filename. It reads all the contents and returns a string that is meant to be the entry.
     * @param fileName
     * @return String
     */
    public String openNote(String fileName) {
        String content = "";
        //try block just in case there is an exception as writing and opening in files can lead to exceptions.
        try {
            InputStream in = openFileInput(fileName);
            if ( in != null) {
                InputStreamReader temp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(temp);
                String str;
                StringBuilder strBuilder = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    strBuilder.append(str + "\n");
                }
                in.close();
                content = strBuilder.toString();
                Log.d(TAG, content);
            }
        } catch (java.io.FileNotFoundException e) {

        } catch (Exception e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        return content;
    }

    /**
     * Overrided from the interface from the adapter. When the note is clicked, an activity is started.
     * intent.putExtra is there to transfer data at the current position/file.
     * @param position
     */
    @Override
    public void onEntryClick(int position) {
        Intent intent = new Intent (this, NoteActivity.class);
        intent.putExtra("entry", allNotes.get(position));
        startActivity (intent);
    }
}
