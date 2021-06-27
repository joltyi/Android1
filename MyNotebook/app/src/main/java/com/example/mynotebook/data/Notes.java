package com.example.mynotebook.data;

import android.os.Parcelable;

import java.util.List;

public interface Notes extends Parcelable {
    List<Note> getNotes();
    Note getNote(int position);
    int getSize();
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearCardData();
}
