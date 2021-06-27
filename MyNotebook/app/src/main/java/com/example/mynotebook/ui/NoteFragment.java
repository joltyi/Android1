package com.example.mynotebook.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynotebook.data.Note;
import com.example.mynotebook.R;
import com.example.mynotebook.utils.DateUtils;

import static com.example.mynotebook.data.Constants.CURRENT_NOTE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private Note currentNote;

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
        View view = inflater.inflate(R.layout.fragment_view_note, container, false);
        TextView titleTextView = view.findViewById(R.id.note_title);
        titleTextView.setText(currentNote.getTitle());
        TextView detailsTextView = view.findViewById(R.id.note_details);
        detailsTextView.setText(currentNote.getDetails());
        TextView dateTextView = view.findViewById(R.id.note_date);
        dateTextView.setText(DateUtils.dateToString(currentNote.getCreateDateTime()));
        return view;
    }
}