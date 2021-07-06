package com.example.mynotebook.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotebook.R;

public class RenameNoteDialogFragment extends DialogFragment {

    private OnRenameListener renameListener;
    private String title;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_rename_note, null);
        EditText editText = view.findViewById(R.id.rename_edit_text);
        editText.setText(title);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.rename_note_title)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.rename_note_done, (d, i) -> {
                    String answer = editText.getText().toString();
                    setTitle(answer);
                    if (renameListener != null) {
                        renameListener.onRename();
                    }
                    dismiss();
                });

        return builder.create();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRenameListener(OnRenameListener renameListener) {
        this.renameListener = renameListener;
    }


}
