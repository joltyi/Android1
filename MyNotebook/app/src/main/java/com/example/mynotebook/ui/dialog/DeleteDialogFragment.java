package com.example.mynotebook.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotebook.MainActivity;
import com.example.mynotebook.R;
import com.example.mynotebook.ui.NotesListFragment;
import com.google.android.material.button.MaterialButton;

import static com.example.mynotebook.data.Constants.DELETE_FRAGMENT_TAG;

public class DeleteDialogFragment extends DialogFragment {

    private OnDeleteListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        listener = (OnDeleteListener) requireActivity().getSupportFragmentManager().findFragmentById(R.id.notes_list_fragment);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setMessage(R.string.delete_note_txt)
                .setPositiveButton(R.string.confirm_delete_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDelete(DeleteDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.delete_note_dialog_button_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCancelDelete(DeleteDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
