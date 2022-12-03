package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private onNoteClickList onNoteClickList;


    public void setOnNoteClickList(NotesAdapter.onNoteClickList onNoteClickList) {
        this.onNoteClickList = onNoteClickList;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }


    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());
        int colorsResId;
        switch (note.getPriority()) {
            case 0:
                colorsResId = android.R.color.holo_green_light;
                break;
            case 1:
                colorsResId = android.R.color.holo_orange_light;
                break;
            default:
                colorsResId = android.R.color.holo_red_light;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorsResId);
        viewHolder.textViewNote.setBackgroundColor(color);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNoteClickList != null) {
                    onNoteClickList.onNoteClick(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

    interface onNoteClickList {
        void onNoteClick(Note note);
    }
}
