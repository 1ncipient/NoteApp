package com.example.henry.henrysosummative.dataModel;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Note class that implements from Parcelable
 */
public class Note implements Parcelable{

    private String title;

    private String entry;

    private String date;

    /**
     * Parameterized Constructor, taking three parameters. The date, title. and entry is passed when initializing a note object.
     * @param title
     * @param entry
     * @param date
     */
    public Note(String title, String entry, String date) {
        this.title = title;
        this.entry = entry;
        this.date = date;
    }

    /**
     * No-argument constructor. Sets default values using mutators.
     */
    public Note() {
        setTitle("Untitled");
        setEntry ("");
        setDate(getDate());
    }

    protected Note(Parcel in) {
        title = in.readString();
        entry = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    /**
     * Accessor for title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mutator for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Accessor for entry
     * @return
     */
    public String getEntry() {
        return entry;
    }

    /**
     * Mutator for entry
     * @param entry
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * Accessor for date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Mutator for date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(entry);
        dest.writeString(date);
    }
}
