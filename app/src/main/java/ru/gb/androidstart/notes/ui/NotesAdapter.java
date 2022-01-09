package ru.gb.androidstart.notes.ui;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.gb.androidstart.notes.domain.NoteEntity;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private OnItemClickListener onItemClickListener;

    private ArrayList<NoteEntity> data = new ArrayList<>();

    public NotesAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData (ArrayList<NoteEntity> data) {
        Collections.reverse(data);
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private NoteEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
