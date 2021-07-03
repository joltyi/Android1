package com.example.mynotebook.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
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
import com.example.mynotebook.data.NotesSource;
import com.example.mynotebook.data.NotesSourceFirebaseImpl;
import com.example.mynotebook.data.NotesSourceImpl;
import com.example.mynotebook.observe.Publisher;

import java.util.Objects;

import static com.example.mynotebook.data.Constants.ALL_NOTES;
import static com.example.mynotebook.data.Constants.DEFAULT_ANIMATION_DURATION;

public class NotesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotesListAdapter adapter;
    private Navigation navigation;
    private Publisher publisher;
    private NotesSource data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        setHasOptionsMenu(true);
        data = new NotesSourceFirebaseImpl().init(it -> adapter.notifyDataSetChanged());
        initRecyclerView(view);
        adapter.setDataSource(data);
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
        adapter = new NotesListAdapter(this);
        recyclerView.setAdapter(adapter);
        addItemDevider();
        setItemAnimator();

        adapter.setOnItemClickListener((view1, position) -> {
            showNote(data.getNote(position));
            publisher.subscribe(note -> {
                data.updateNote(position, note);
                adapter.notifyItemChanged(position);
            });
        });

        adapter.setOnItemLongClickListener((view1, position) -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view1);
            Menu menu = popupMenu.getMenu();
            activity.getMenuInflater().inflate(R.menu.popup_menu, menu);
            popupMenu.setOnMenuItemClickListener(item -> selectPopupMenuItem(item.getItemId(), position));
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return selectMenuItem(menuItem.getItemId()) || super.onOptionsItemSelected(menuItem);
    }

    private void showNote(Note note) {
        navigation.addFragment(NoteFragment.newInstance(note), true);
    }

    private boolean selectMenuItem(int itemId) {
        switch (itemId) {
            case R.id.menu_add:
                navigation.addFragment(new NoteFragment(), true);
                publisher.subscribe(note -> {
                    data.addNote(note);
                    adapter.notifyItemInserted(data.getSize() - 1);
                    recyclerView.scrollToPosition(data.getSize() - 1);
                });
                return true;
            case R.id.menu_sort:
                Toast.makeText(getContext(), R.string.menu_sort, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_search:
                Toast.makeText(getContext(), R.string.menu_search, Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    private boolean selectPopupMenuItem(int itemId, int position) {
        switch (itemId) {
            case R.id.popup_edit:
                showNote(data.getNote(position));
                publisher.subscribe(n -> {
                    data.updateNote(position, n);
                    adapter.notifyItemChanged(position);
                });
                return true;
            case R.id.popup_delete:
                Toast.makeText(getContext(),
                        String.join(" ", getResources().getString(R.string.delete), String.valueOf(position)),
                        Toast.LENGTH_SHORT).show();
                data.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
            case R.id.popup_clone:
                showNote(data.getNote(position));
                publisher.subscribe(n -> {
                    data.addNote(n);
                    adapter.notifyItemInserted(data.getSize() - 1);
                    recyclerView.scrollToPosition(data.getSize() - 1);
                });
                return true;
        }
        return true;
    }
}