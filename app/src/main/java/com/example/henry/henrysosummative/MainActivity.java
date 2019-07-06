/**
 * This activity is the MainActivity. It is the first screen (activity) of the app. This is the activity on startup
 * Contains two editable textboxes; one for title, one for contents. It has a floating action button to confirm the note
 * details and saves it to the internal memory as a .txt file. It also has a heart checkbox that currently does not have any functionality.
 * There is a navigation menu that allows the user to navigate to all notes or favorite notes.
 */

package com.example.henry.henrysosummative;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

// Import our Widget classes
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

// Import Event handling classes for CheckBox widget
import android.view.View.OnClickListener;

import com.example.henry.henrysosummative.dataModel.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This activity is the main screen; the startup screen. It extends to AppCompatActivity and implements NavigationView, which
 * allows for a NavigationMenu in the app.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText title;
    private EditText entry;
    private TextView date;
    private CheckBox favoriteCheckBox;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private boolean checkBoxSelected;
    private int numNotes = 1;
    private SharedPreferences savedPrefs;

    private String titleString;
    private String entryString;
    /**
     * Called on startup
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Acquires the date, and formats it so that the TextView widget references the date by yyyy-mm-dd
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(currentDate);
        date = findViewById(R.id.dateLabel);
        date.setText(strDate);

        title = findViewById(R.id.titleEditText);
        entry = findViewById(R.id.noteEditText);


        favoriteCheckBox = findViewById(R.id.favoriteCheckBox);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = findViewById(R.id.fab);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        //set Listeners
        favoriteCheckBox.setOnClickListener(new CheckBoxListener());
        fab.setOnClickListener (new fabListener());

        savedPrefs = getSharedPreferences( "NotePreferences", MODE_PRIVATE );
    }

    @Override
    public void onPause() {
        // Save the  instance variables
        Editor prefsEditor = savedPrefs.edit();
        prefsEditor.putString( "titleString", titleString );
        prefsEditor.putString( "entryString", entryString);
        prefsEditor.commit();

        // Calling the parent onPause() must be done LAST
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load the instance variables back (or default values)
        titleString = savedPrefs.getString("titleString", title.getText().toString());
        entryString = savedPrefs.getString("entryString", entry.getText().toString());

        // Format/update these values in our widgets
        title.setText(titleString);
        entry.setText(entryString);
    }

    /**
     * This method saves the file to the internal storage when the check mark is pressed.
     * @param file
     */
    private void save (String file) {
        //try block just in case there is an exception when writing in files.
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(file, 0));
            out.write (entry.getText().toString());
            out.close ();
        }
        catch (Exception e) {
            Toast.makeText(MainActivity.this, "Exception! "+e.toString() ,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When the back button is pressed, the navigation menu will close
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * This public boolean method controls the navigation menu. When selecting one of the options of all notes or favorites, you are taken
     * to a new screen specifically tailored for those menus
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //go to all notes screen
        if (id == R.id.nav_all_notes_label) {
            startActivity (new Intent (MainActivity.this, AllNotesActivity.class));
        }
        //go to favorites screen
        else if (id == R.id.nav_favorites_label) {
            startActivity (new Intent (MainActivity.this, FavoriteNotesActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * CheckBoxListener. A toast message will play when the heart is clicked.
     * However, the heart doesn't currently have any functionality in this version.
     */
    class CheckBoxListener implements OnClickListener {
        @Override
        public void onClick( View v ) {
            if (v.getId() == R.id.favoriteCheckBox) {
                if (favoriteCheckBox.isChecked()) {
                    Toast.makeText( MainActivity.this, "Favourited!",
                            Toast.LENGTH_SHORT).show();
                    checkBoxSelected = true;
                }
                else {
                    Toast.makeText(MainActivity.this, "Unfavourited!",
                            Toast.LENGTH_SHORT).show();
                    checkBoxSelected = false;
                }
            }
        }
    }

    /**
     * When the checkmark is pressed AKA the Floating Action Button, the text will be saved
     */
    class fabListener implements OnClickListener {
        @Override
        public void onClick (View v) {
            if (v.getId()== R.id.fab) {
                //save the note to a file
                save(title.getText()+".txt");
                Toast.makeText(MainActivity.this,"Note Saved!", Toast.LENGTH_SHORT).show();
                //reset EditText
                title.setText ("");
                entry.setText ("");
            }
        }
    }


}
