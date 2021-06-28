package com.example.mynotebook.data;

import android.content.res.Resources;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotesImpl implements Notes {
    private List<Note> notes;
    private Resources resources;

    public NotesImpl(Resources resources) {
        this.resources = resources;
        notes = new ArrayList<Note>() {
            {
                add(new Note("My Note #1", "My Note #1 Details", LocalDateTime.now(), true));
                add(new Note("My Note #2", "My Note #2 Details", LocalDateTime.now().minusYears(1), false));
                add(new Note("My Note #3", "My Note #3 Details", LocalDateTime.now().minusYears(2), false));
                add(new Note("My Note #4", "My Note #4 Details", LocalDateTime.now().minusHours(2), false));
                add(new Note("My Note #5", "My Note #5 Details", LocalDateTime.now().minusMonths(3), false));
            }
        };
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
}
