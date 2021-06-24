package com.example.mynotebook.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final List<MyNote> MY_NOTES = new ArrayList<MyNote>() {
        {
            add(new MyNote("My Note #1", "My Note #1 Details", LocalDateTime.now(), true));
            add(new MyNote("My Note #2", "My Note #2 Details", LocalDateTime.now().minusYears(1), false));
            add(new MyNote("My Note #3", "My Note #3 Details", LocalDateTime.now().minusYears(2), false));
            add(new MyNote("My Note #4", "My Note #4 Details", LocalDateTime.now().minusHours(2), false));
            add(new MyNote("My Note #5", "My Note #5 Details", LocalDateTime.now().minusMonths(3), false));
        }
    };

    public static final String CURRENT_NOTE = "CURRENT_NOTE";
    public static final String NOTES_LIST = "NOTES_LIST";
}