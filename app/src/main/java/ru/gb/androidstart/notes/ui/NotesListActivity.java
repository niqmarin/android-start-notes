package ru.gb.androidstart.notes.ui;

import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;
import ru.gb.androidstart.notes.impl.NotesStorageImpl;
import ru.gb.androidstart.notes.ui.NoteScreenActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListActivity extends AppCompatActivity {
    private FloatingActionButton addNoteButton;
    private NotesStorage notesStorage = new NotesStorageImpl();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        
        initViews();
        setClickListeners();
        
    }

    private void initViews() {
        addNoteButton = findViewById(R.id.add_note_button);
    }

    private void setClickListeners() {
        addNoteButton.setOnClickListener(v -> openNewNote());
    }

    private void openNewNote() {
        NoteScreenActivity.openNewNote(this);
    }

}