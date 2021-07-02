package com.example.mynotebook.data;

import android.content.res.Resources;
import android.os.Parcel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotesSourceImpl implements NotesSource {
    private List<Note> notes;
    private Resources resources;

    public NotesSourceImpl(Resources resources) {
        this.resources = resources;
    }

    protected NotesSourceImpl(Parcel in) {
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NotesSourceImpl> CREATOR = new Creator<NotesSourceImpl>() {
        @Override
        public NotesSourceImpl createFromParcel(Parcel in) {
            return new NotesSourceImpl(in);
        }

        @Override
        public NotesSourceImpl[] newArray(int size) {
            return new NotesSourceImpl[size];
        }
    };

    @Override
    public NotesSource init(NotesSourceResponce cardsSourceResponse) {
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
        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }
        return this;
    }

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
