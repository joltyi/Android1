package com.example.mynotebook.data;

import java.util.List;

public interface Notes {
    List<Note> getNotes();
    Note getNote(int position);
    int getSize();
}
