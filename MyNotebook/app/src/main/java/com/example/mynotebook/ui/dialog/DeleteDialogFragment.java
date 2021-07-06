package com.example.mynotebook.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotebook.R;
import com.google.android.material.button.MaterialButton;

public class DeleteDialogFragment extends DialogFragment {

    private OnDeleteListener deleteListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().
                inflate(R.layout.dialog_delete_note, null);
        MaterialButton confirmDelete = contentView.findViewById(R.id.confirm_delete_note_dialog_button);
        confirmDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete();
                dismiss();
            }
        });
        MaterialButton cancelDelete = contentView.findViewById(R.id.no_delete_note_dialog_button);
        cancelDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onCancelDelete();
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(contentView);
        return builder.create();
    }

    public void setOnDialogListener(OnDeleteListener deleteDlgListener) {
        this.deleteListener = deleteDlgListener;
    }
}
