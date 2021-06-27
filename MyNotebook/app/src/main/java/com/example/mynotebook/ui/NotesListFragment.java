package com.example.mynotebook.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotebook.MainActivity;
import com.example.mynotebook.Navigation;
import com.example.mynotebook.data.Note;
import com.example.mynotebook.R;
import com.example.mynotebook.data.Notes;
import com.example.mynotebook.data.NotesImpl;

import java.util.Objects;

import static com.example.mynotebook.data.Constants.CURRENT_NOTE;

public class NotesListFragment extends Fragment {

    private Navigation navigation;
    private Note currentNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        Notes notes = new NotesImpl(getResources());
        currentNote = notes.getNote(0);
        initRecyclerView(view, notes);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
    }

    @Override
    public void onDetach() {
        navigation = null;
        super.onDetach();
    }

    private void initRecyclerView(View view, Notes notes) {
        RecyclerView recyclerView = view.findViewById(R.id.notes_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NotesListAdapter adapter = new NotesListAdapter(notes);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, getActivity().getTheme())));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setTitleOnItemClickListener((view1, position) -> {
            currentNote = notes.getNote(position);
            showNote(currentNote);
            Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
        });

        adapter.setDateOnItemClickListener((view1, position) -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view1);
            Menu menu = popupMenu.getMenu();
            activity.getMenuInflater().inflate(R.menu.popup_menu, menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.popup_edit:
                        showNote(currentNote);
                        Toast.makeText(getContext(), R.string.popup_edit, Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, currentNote);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        }
    }

    private void showNote(Note note) {
        navigation.addFragment(NoteFragment.newInstance(note), true);
    }

}