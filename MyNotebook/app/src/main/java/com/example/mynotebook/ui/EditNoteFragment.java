package com.example.mynotebook.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynotebook.data.Note;
import com.example.mynotebook.R;

import static com.example.mynotebook.data.Constants.CURRENT_NOTE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNoteFragment extends Fragment {

    private Note currentNote;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        TextView titleTextView = view.findViewById(R.id.edit_note);
        titleTextView.setText(String.format("Stub Edit Note: %s ", currentNote.getTitle()));
        return view;
    }
}