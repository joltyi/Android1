package com.example.mynotebook.data;

import com.example.mynotebook.utils.DateUtils;
import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NoteMapping {

    public static class Fields {
        public final static String TITLE = "title";
        public final static String DETAILS = "details";
        public final static String DATE = "date";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        assert timeStamp != null;
        LocalDateTime localDateTime = DateUtils.toLocalDateTime(timeStamp.toDate());

        Note answer = new Note((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DETAILS),
                localDateTime);
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, note.getTitle());
        answer.put(Fields.DETAILS, note.getDetails());
        answer.put(Fields.DATE, DateUtils.toDate(note.getCreateDateTime()));
        return answer;
    }


}
