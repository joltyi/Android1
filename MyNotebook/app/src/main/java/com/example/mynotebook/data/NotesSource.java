package com.example.mynotebook.data;

public interface NotesSource {
    NotesSource init(NotesSourceResponse cardsSourceResponse);
    Note getNote(int position);
    int getSize();
    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
}
