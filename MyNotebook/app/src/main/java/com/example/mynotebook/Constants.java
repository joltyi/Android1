package com.example.mynotebook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final List<MyNote> MY_NOTES = new ArrayList<MyNote>() {
        {
            add(new MyNote("My Note #1", "My Note #1 Details", LocalDateTime.now()));
            add(new MyNote("My Note #2", "My Note #2 Details", LocalDateTime.now().minusDays(1)));
            add(new MyNote("My Note #3", "My Note #3 Details", LocalDateTime.now().minusDays(2)));
        }
    };

    public static final String CURRENT_NOTE = "CURRENT_NOTE";
    public static final String NOTES_LIST = "NOTES_LIST";
}
