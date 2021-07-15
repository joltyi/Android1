package com.example.mynotebook.ui.dialog;

import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public interface OnDeleteListener extends Serializable {
    void onDelete(DialogFragment dialog);
    void onCancelDelete(DialogFragment dialog);
}
