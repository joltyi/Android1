package com.example.mynotebook.data;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotesImpl implements Notes {
    private List<Note> notes;
    private Resources resources;

    public NotesImpl(Resources resources) {
        this.resources = resources;
        //TODO get from resources
        notes = new ArrayList<Note>() {
            {
                add(new Note("My Note #1", "My Note #1 Details", LocalDateTime.now()));
                add(new Note("My Note #2", "My Note #2 Details", LocalDateTime.now().minusYears(1)));
                add(new Note("My Note #3", "My Note #3 Details", LocalDateTime.now().minusYears(2)));
                add(new Note("My Note #4", "My Note #4 Details", LocalDateTime.now().minusHours(2)));
                add(new Note("My Note #5", "My Note #5 Details", LocalDateTime.now().minusMonths(3)));
            }
        };
    }

    protected NotesImpl(Parcel in) {
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NotesImpl> CREATOR = new Creator<NotesImpl>() {
        @Override
        public NotesImpl createFromParcel(Parcel in) {
            return new NotesImpl(in);
        }

        @Override
        public NotesImpl[] newArray(int size) {
            return new NotesImpl[size];
        }
    };

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int getSize() {
        return notes.size();
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notes.set(position, note);
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public void clearCardData() {
        notes.clear();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }
}
