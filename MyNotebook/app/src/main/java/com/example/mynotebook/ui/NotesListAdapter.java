package com.example.mynotebook.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotebook.R;
import com.example.mynotebook.data.Note;
import com.example.mynotebook.data.NotesSource;
import com.example.mynotebook.utils.DateUtils;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> {

    private NotesSource notesSource;
    private Fragment fragment;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public NotesListAdapter(NotesSource notesSource, Fragment fragment){
        this.notesSource = notesSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(notesSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return notesSource.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener){
        this.itemLongClickListener = itemLongClickListener;
    }

    private void registerContextMenu(View itemView) {
        if (fragment != null) {
            itemView.setOnLongClickListener(v -> {
                return false;
            });
            fragment.registerForContextMenu(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cardLayout;
        private TextView title;
        private TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            registerContextMenu(itemView);
            title = itemView.findViewById(R.id.card_title_text_view);
            date = itemView.findViewById(R.id.card_date_text_view);
            cardLayout = itemView.findViewById(R.id.card_layout_main);
            cardLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            cardLayout.setOnLongClickListener(v -> {
                if (itemLongClickListener != null) {
                    itemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return true;
            });
        }

        public void setData(Note note){
            title.setText(note.getTitle());
            date.setText(DateUtils.dateToString(note.getCreateDateTime()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
