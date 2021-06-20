package com.example.mynotebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.mynotebook.Constants.CURRENT_NOTE;
import static com.example.mynotebook.Constants.NOTES_LIST;

public class NotesListFragment extends Fragment {

    private Map<MyNote, TextView> notes;
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
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, getCurrentNote());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            setCurrentNote(savedInstanceState.getParcelable(CURRENT_NOTE));
        }
        if (isLandscape) {
            showLandscapeNoteDetails(getCurrentNote());
        }
    }

    private MyNote getCurrentNote() {
        return notes.keySet()
                .stream()
                .filter(MyNote::getCurrent)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Current Note"));
    }

    private void setCurrentNote(MyNote note) {
        notes.keySet().forEach(n -> n.setCurrent(false));
        notes.keySet()
                .stream()
                .filter(n -> n.equals(note))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Current Note"))
                .setCurrent(true);
    }

    private void initNotesList(View view) {
        notes = new HashMap<>();
        Constants.MY_NOTES
                .forEach(note -> {
                    Context context = getContext();
                    if (context != null) {
                        LinearLayout layout = (LinearLayout) view;
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        TextView titleTextView = initTitleTextView(context, note);
                        notes.put(note, titleTextView);
                        layout.addView(titleTextView);
                        layout.addView(initDateTextView(context, note));
                    }
                });
    }

    private TextView initTitleTextView(Context context, MyNote note) {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView titleView = new TextView(context);
        titleView.setLayoutParams(lparams);
        titleView.setText(note.getTitle());
        titleView.setTextSize(getResources().getInteger(R.integer.list_title_text_size));
        if (note.getCurrent()) {
            titleView.setTextSize(getResources().getInteger(R.integer.list_title_selected_text_size));
        }
        titleView.setTextColor(getResources().getColor(R.color.teal_700, context.getTheme()));
        titleView.setPadding(getResources().getInteger(R.integer.list_title_padding_left),
                getResources().getInteger(R.integer.list_title_padding_top),
                getResources().getInteger(R.integer.list_title_padding_right),
                getResources().getInteger(R.integer.list_title_padding_bottom));
        titleView.setOnClickListener(v -> showNoteDetails(note));
        return titleView;
    }

    private TextView initDateTextView(Context context, MyNote note) {
        TextView dateView = new TextView(context);
        dateView.setText(Utils.dateToString(note.getCreateDateTime()));
        dateView.setTextColor(getResources().getColor(R.color.teal_200, context.getTheme()));
        return dateView;
    }

    private void showNoteDetails(MyNote note) {
        setCurrentNote(note);
        if (isLandscape) {
            notes.forEach((note1, titleView) -> {
                titleView.setTextSize(getResources().getInteger(R.integer.list_title_text_size));
                if (note1.getCurrent()) {
                    titleView.setTextSize(getResources().getInteger(R.integer.list_title_selected_text_size));
                }
            });
            showLandscapeNoteDetails(note);
        } else {
            showPortraitNoteDetails(note);
        }
    }

    private void showPortraitNoteDetails(MyNote note) {
        NoteDetailsFragment fragment = NoteDetailsFragment.newInstance(note);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(NOTES_LIST)
                .replace(R.id.notes_list_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showLandscapeNoteDetails(MyNote note) {
        NoteDetailsFragment fragment = NoteDetailsFragment.newInstance(note);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_details_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}