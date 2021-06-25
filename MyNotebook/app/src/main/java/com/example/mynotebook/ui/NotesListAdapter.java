package com.example.mynotebook.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotebook.R;
import com.example.mynotebook.data.Note;
import com.example.mynotebook.data.Notes;
import com.example.mynotebook.utils.DateUtils;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> {

    private OnItemClickListener titleClickListener;
    private OnItemClickListener dateClickListener;
    private Notes notes;

    public NotesListAdapter(Notes notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(notes.getNote(position));
    }

    @Override
    public int getItemCount() {
        return notes.getSize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout;
        private TextView title;
        private TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.card_layout_main);
            initTitle(itemView);
            initDate(itemView);
        }

        private void initDate(View itemView) {
            date = itemView.findViewById(R.id.card_date_text_view);
            date.setOnClickListener(v -> {
                if (dateClickListener != null) {
                    dateClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private void initTitle(View itemView) {
            title = itemView.findViewById(R.id.card_title_text_view);
            title.setOnClickListener(v -> {
                if (titleClickListener != null) {
                    titleClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(Note note){
            title.setText(note.getTitle());
            date.setText(DateUtils.dateToString(note.getCreateDateTime()));
        }
    }

    public void setTitleOnItemClickListener(OnItemClickListener itemClickListener){
        this.titleClickListener = itemClickListener;
    }

    public void setDateOnItemClickListener(OnItemClickListener itemClickListener){
        this.dateClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
