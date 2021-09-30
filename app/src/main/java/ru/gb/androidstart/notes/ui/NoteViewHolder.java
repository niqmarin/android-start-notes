package ru.gb.androidstart.notes.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView = itemView.findViewById(R.id.note_title_text_view);
    public TextView contentsTextView = itemView.findViewById(R.id.note_contents_text_view);
    public TextView dateTextView = itemView.findViewById(R.id.note_date_text_view);

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
