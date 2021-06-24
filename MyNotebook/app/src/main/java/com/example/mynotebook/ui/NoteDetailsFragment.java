package com.example.mynotebook.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynotebook.data.MyNote;
import com.example.mynotebook.R;
import com.example.mynotebook.utils.DateUtils;

import static com.example.mynotebook.data.Constants.CURRENT_NOTE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailsFragment extends Fragment {

    private MyNote currentNote;

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
            currentNote = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        TextView titleTextView = view.findViewById(R.id.note_title);
        titleTextView.setText(currentNote.getTitle());
        TextView detailsTextView = view.findViewById(R.id.note_details);
        detailsTextView.setText(currentNote.getDetails());
        TextView dateTextView = view.findViewById(R.id.note_date);
        dateTextView.setText(DateUtils.dateToString(currentNote.getCreateDateTime()));
        return view;
    }
}