package com.example.mynotebook.ui;

import android.annotation.SuppressLint;
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
import com.example.mynotebook.observe.Publisher;
import com.example.mynotebook.ui.dialog.DeleteDialogFragment;
import com.example.mynotebook.ui.dialog.OnDeleteListener;
import com.example.mynotebook.ui.dialog.OnRenameListener;
import com.example.mynotebook.ui.dialog.RenameNoteDialogFragment;

import java.util.Objects;

import static com.example.mynotebook.data.Constants.DEFAULT_ANIMATION_DURATION;
import static com.example.mynotebook.data.Constants.DELETE_FRAGMENT_TAG;
import static com.example.mynotebook.data.Constants.RENAME_NOTE_TAG;

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

        adapter.setOnItemClickListener((view1, position) -> updateNote(position));
        adapter.setOnItemLongClickListener(this::showPopUpMenu);
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

    @SuppressLint("NonConstantResourceId")
    private boolean selectMenuItem(int itemId) {
        switch (itemId) {
            case R.id.menu_add:
                return addNote();
            case R.id.menu_sort:
                return sortNotes();
            case R.id.menu_search:
                return searchNote();
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean selectPopupMenuItem(int itemId, int position) {
        switch (itemId) {
            case R.id.popup_edit:
                return updateNote(position);
            case R.id.popup_rename:
                return renameNote(position);
            case R.id.popup_delete:
                return deleteNote(position);
            case R.id.popup_clone:
                return cloneNote(position);
        }
        return true;
    }

    private void showNoteDetails(int position) {
        navigation.addFragment(NoteFragment.newInstance(data.getNote(position)), true);
    }

    private boolean addNote() {
        navigation.addFragment(new NoteFragment(), true);
        publisher.subscribe(note -> {
            data.addNote(note);
            adapter.notifyItemInserted(data.getSize() - 1);
            recyclerView.scrollToPosition(data.getSize() - 1);
        });
        return true;
    }

    private boolean updateNote(int position) {
        showNoteDetails(position);
        publisher.subscribe(n -> {
            data.updateNote(position, n);
            adapter.notifyItemChanged(position);
        });
        return true;
    }

    private boolean renameNote(int position) {
        Note note = data.getNote(position);
        RenameNoteDialogFragment renameDialog = new RenameNoteDialogFragment();
        renameDialog.setTitle(note.getTitle());
        renameDialog.setRenameListener(() -> {
            note.setTitle(renameDialog.getTitle());
            data.updateNote(position, note);
            adapter.notifyItemChanged(position);
        });
        renameDialog.show(requireActivity().getSupportFragmentManager(), RENAME_NOTE_TAG);
        return true;
    }

    private boolean cloneNote(int position) {
        showNoteDetails(position);
        publisher.subscribe(n -> {
            data.addNote(n);
            adapter.notifyItemInserted(data.getSize() - 1);
            recyclerView.scrollToPosition(data.getSize() - 1);
        });
        return true;
    }

    private boolean deleteNote(int position) {
        DeleteDialogFragment deleteDlgFragment = new DeleteDialogFragment();
        deleteDlgFragment.setCancelable(false);
        deleteDlgFragment.setOnDialogListener(new OnDeleteListener() {
            @Override
            public void onDelete() {
                data.deleteNote(position);
                adapter.notifyItemRemoved(position);
                deleteDlgFragment.dismiss();
            }

            @Override
            public void onCancelDelete() {
                deleteDlgFragment.dismiss();
            }
        });
        deleteDlgFragment.show(requireActivity().getSupportFragmentManager(), DELETE_FRAGMENT_TAG);
        return true;
    }

    private boolean sortNotes() {
        Toast.makeText(getContext(), R.string.menu_sort, Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean searchNote() {
        Toast.makeText(getContext(), R.string.menu_search, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showPopUpMenu(View view, int position) {
        Activity activity = requireActivity();
        PopupMenu popupMenu = new PopupMenu(activity, view);
        Menu menu = popupMenu.getMenu();
        activity.getMenuInflater().inflate(R.menu.popup_menu, menu);
        popupMenu.setOnMenuItemClickListener(item -> selectPopupMenuItem(item.getItemId(), position));
        popupMenu.show();
    }
}