package com.example.henry.henrysosummative.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import com.example.henry.henrysosummative.R;
import com.example.henry.henrysosummative.dataModel.Note;

import java.util.ArrayList;

/**
 * Adapter class. Extends from RecyclerView. This class allows for recyclerView to display the list of entries in a vertical layout
 * As you scroll down, the recyclerView gets rid of notes you cannot see (efficiency).
 */
public class EntryRecyclerAdapter extends RecyclerView.Adapter<EntryRecyclerAdapter.ViewHolder>{

    private ArrayList <Note> entries = new ArrayList<>();
    private OnNoteListener onNoteListener;

    /**
     * Parameterized Constructor, taking an Arraylist and OnNoteListener as parameters.
     * @param entries the arraylist is initialized
     * @param onNoteListener the onNoteListener is initialized.
     */
    public EntryRecyclerAdapter (ArrayList <Note> entries, OnNoteListener onNoteListener) {
        this.entries = entries;
        this.onNoteListener = onNoteListener;

    }

    /**
     * onCreateViewHolder holds the amount of notes in view.
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_single_note, viewGroup, false);
        return new ViewHolder (view, onNoteListener);
    }

    /**
     * Sets the date and title of the notes by utilizing its accessor methods
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.entryDate.setText(entries.get(i).getDate());
        viewHolder.entryTitle.setText(entries.get(i).getTitle());
    }

    /**
     * This public method returns an int that returns the amount of items in the array.
     * @return
     */
    @Override
    public int getItemCount() {
        return entries.size();
    }

    /**
     * ViewHolder class. Extends from RecyclerView and implements View.OnClickListener
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView entryTitle;
        private TextView entryDate;

        OnNoteListener onNoteListener;

        /**
         * Viewholder is the constructor that initializes the entryTitle and entryDate's widgets.
         * @param itemView
         * @param onNoteListener
         */
        public ViewHolder (View itemView, OnNoteListener onNoteListener ) {
            super (itemView);
            entryTitle = itemView.findViewById(R.id.entry_title);
            entryDate = itemView.findViewById(R.id.entry_date);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        /**
         * Method that is overrided. When an entry is clicked, go to the NoteActivity
         * @param view
         */
        @Override
        public void onClick(View view) {
            onNoteListener.onEntryClick(getAdapterPosition());
        }
    }

    /**
     * Interface; Overrided in the AllNotesActivity.
     */
    public interface OnNoteListener {
        void onEntryClick (int position);
    }
}
