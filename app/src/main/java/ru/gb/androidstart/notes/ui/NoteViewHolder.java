package ru.gb.androidstart.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView = itemView.findViewById(R.id.note_title_text_view);
    private TextView contentsTextView = itemView.findViewById(R.id.note_contents_text_view);
    private TextView dateTextView = itemView.findViewById(R.id.note_date_text_view);
    private NoteEntity note;

    private OnItemClickListener onItemClickListener;

    private static final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", myDateFormatSymbols);

    public NoteViewHolder(@NonNull ViewGroup parent, OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_note_layout, parent, false));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(note);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongClick(view, note);
                return true;
            }
        });
    }

    public void bind (NoteEntity note) {
        this.note = note;
        titleTextView.setText(note.getTitle());
        contentsTextView.setText(note.getContents());
        dateTextView.setText(dateFormat.format(note.getDate()));
    }
}
