package ru.gb.androidstart.notes.ui;

import android.view.View;

import ru.gb.androidstart.notes.domain.NoteEntity;

public interface OnItemClickListener {

        void onItemClick(NoteEntity note);
        void onItemLongClick(View view, NoteEntity note);

}
