package com.example.mynotebook.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mynotebook.MainActivity;
import com.example.mynotebook.data.Note;
import com.example.mynotebook.R;
import com.example.mynotebook.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static com.example.mynotebook.data.Constants.CURRENT_NOTE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private Note currentNote;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);

        if (currentNote != null) {
            populateView();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        currentNote = getCurrentNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(currentNote);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }


    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        title.setText(currentNote.getTitle());
        description.setText(currentNote.getDetails());
        LocalDateTime dateTime = currentNote.getCreateDateTime();
        datePicker.init(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), null);
    }

    private Note getCurrentNote(){
        Editable titleRaw = this.title.getText();
        String title = titleRaw == null ? "" : titleRaw.toString();

        Editable descriptionRaw = this.description.getText();
        String description = descriptionRaw == null ? "" : descriptionRaw.toString();

        LocalTime time = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.of(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), time.getHour(), time.getMinute());

        Note newNote = new Note(title, description, localDateTime);;
        if (currentNote != null) {
            newNote.setId(currentNote.getId());
        }
        return newNote;
    }
}