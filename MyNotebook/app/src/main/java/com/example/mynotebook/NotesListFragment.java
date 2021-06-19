package com.example.mynotebook;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.mynotebook.Constants.CURRENT_NOTE;
import static com.example.mynotebook.Constants.NOTES_LIST;

public class NotesListFragment extends Fragment {

    private List<MyNote> notes;
    private MyNote currentNote;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        initNotesList(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = notes.get(0);
        }
        if (isLandscape) {
//            showLandNote(currentNote);

        }
    }

    private void initNotesList(View view) {
        notes = Constants.MY_NOTES;
        notes.forEach(note -> {
            Context context = getContext();
            if (context != null) {
                LinearLayout layout = (LinearLayout) view;
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                layout.addView(initTitleTextView(context, note));
                layout.addView(initDateTextView(context, note));
            }
        });
    }

    private TextView initTitleTextView(Context context, MyNote note) {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView titleView = new TextView(context);
        titleView.setLayoutParams(lparams);
        titleView.setText(note.getTitle());
        titleView.setTextSize(20);
        titleView.setTextColor(getResources().getColor(R.color.teal_700, context.getTheme()));
        titleView.setPadding(0, 30, 0, 0);
        titleView.setOnClickListener(v -> initCurrentNote(note));
        return titleView;
    }

    private TextView initDateTextView(Context context, MyNote note) {
        TextView dateView = new TextView(context);
        dateView.setText(Utils.dateToString(note.getCreateDateTime()));
        dateView.setTextColor(getResources().getColor(R.color.teal_200, context.getTheme()));
        return dateView;
    }

    private void initCurrentNote(MyNote note) {
        currentNote = note;
        showNote(note);
    }

    private void showNote(MyNote currentNote) {
        if (isLandscape) {
//            showLandNote(currentNote);
        } else {
            showPortraitNote(currentNote);
        }
    }

    private void showPortraitNote(MyNote currentNote) {
        NoteDetailsFragment fragment = NoteDetailsFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(NOTES_LIST);
        fragmentTransaction.replace(R.id.notes_list, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}