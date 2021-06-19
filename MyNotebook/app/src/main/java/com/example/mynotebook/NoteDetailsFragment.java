package com.example.mynotebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailsFragment extends Fragment {

    private static final String CURRENT_NOTE = "CurrentNote";

    private MyNote myNote;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    public static NoteDetailsFragment newInstance(MyNote myNote) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, myNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myNote = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        TextView titleTextView = view.findViewById(R.id.note_title);
        titleTextView.setText(myNote.getTitle());
        TextView detailsTextView = view.findViewById(R.id.note_details);
        detailsTextView.setText(myNote.getDetails());
        TextView dateTextView = view.findViewById(R.id.note_date);
        dateTextView.setText(Utils.dateToString(myNote.getCreateDateTime()));
        return view;
    }
}