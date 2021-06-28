package com.example.mynotebook.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mynotebook.MainActivity;
import com.example.mynotebook.Navigation;
import com.example.mynotebook.data.Note;
import com.example.mynotebook.R;
import com.example.mynotebook.data.Notes;
import com.example.mynotebook.data.NotesImpl;
import com.example.mynotebook.observe.Observer;
import com.example.mynotebook.observe.Publisher;

import java.util.Objects;

import static com.example.mynotebook.data.Constants.ALL_NOTES;
import static com.example.mynotebook.data.Constants.CURRENT_NOTE;
import static com.example.mynotebook.data.Constants.DEFAULT_ANIMATION_DURATION;
import static com.example.mynotebook.data.Constants.NOTES_LIST;

public class NotesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotesListAdapter adapter;
    private Navigation navigation;
    private Publisher publisher;
    private Notes notes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (notes == null) {
            notes = new NotesImpl(getResources());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        setHasOptionsMenu(true);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.notes_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotesListAdapter(notes, this);
        recyclerView.setAdapter(adapter);
        addItemDevider();
        setItemAnimator();

        adapter.setOnItemClickListener((view1, position) -> {
            showNote(notes.getNote(position));
            publisher.subscribe(note -> {
                notes.updateNote(position, note);
                adapter.notifyItemChanged(position);
            });
        });

        adapter.setOnItemLongClickListener((view1, position) -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view1);
            Menu menu = popupMenu.getMenu();
            activity.getMenuInflater().inflate(R.menu.popup_menu, menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.popup_edit:
                        showNote(notes.getNote(position));
                        publisher.subscribe(n -> {
                            notes.updateNote(position, n);
                            adapter.notifyItemChanged(position);
                        });
                        return true;
                    case R.id.popup_delete:
                        Toast.makeText(getContext(),
                                String.join(" ", getResources().getString(R.string.delete), String.valueOf(position)),
                                Toast.LENGTH_SHORT).show();
                        notes.deleteNote(position);
                        adapter.notifyItemRemoved(position);
                        return true;
                    case R.id.popup_clone:
                        showNote(notes.getNote(position));
                        publisher.subscribe(n -> {
                            notes.addNote(n);
                            adapter.notifyItemInserted(notes.getSize() - 1);
                            recyclerView.scrollToPosition(notes.getSize() - 1);
                        });
                        return true;
                }
                return true;
            });
            popupMenu.show();

        });
    }

    private void addItemDevider() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, getActivity().getTheme())));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void setItemAnimator() {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(DEFAULT_ANIMATION_DURATION);
        animator.setChangeDuration(DEFAULT_ANIMATION_DURATION);
        animator.setRemoveDuration(DEFAULT_ANIMATION_DURATION);
        recyclerView.setItemAnimator(animator);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ALL_NOTES, notes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            notes = savedInstanceState.getParcelable(ALL_NOTES);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.menu_add:
                navigation.addFragment(new NoteFragment(), true);
                publisher.subscribe(note -> {
                    notes.addNote(note);
                    adapter.notifyItemInserted(notes.getSize() - 1);
                    recyclerView.scrollToPosition(notes.getSize() - 1);
                });
                return true;
            case R.id.menu_sort:
                Toast.makeText(getContext(), R.string.menu_sort, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_search:
                Toast.makeText(getContext(), R.string.menu_search, Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showNote(Note note) {
        navigation.addFragment(NoteFragment.newInstance(note), true);
//        NoteFragment fragment = NoteFragment.newInstance(note);
//        requireActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack(NOTES_LIST)
//                .replace(R.id.notes_list_fragment, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
    }

}