package ru.gb.androidstart.notes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;
import ru.gb.androidstart.notes.impl.NotesStorageImpl;
import ru.gb.androidstart.notes.ui.NoteScreenActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesListActivity extends AppCompatActivity {
    private FloatingActionButton addNoteButton;
    private RecyclerView notesRecycleView;
    private NotesStorage notesStorage = new NotesStorageImpl();
    private NotesAdapter notesAdapter = new NotesAdapter();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        addTestNotes();

        initViews();
        setClickListeners();
    }

    private void initViews() {
        addNoteButton = findViewById(R.id.add_note_button);
        notesRecycleView = findViewById(R.id.notes_list_recycle_view);
        notesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        notesRecycleView.setAdapter(notesAdapter);
        notesAdapter.setData(notesStorage.getNotesList());
    }

    private void setClickListeners() {
        addNoteButton.setOnClickListener(v -> openNewNote());
        notesAdapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(NoteEntity item) {
        openSelectedNote(item);
    }

    private void openNewNote() {
        NoteScreenActivity.openNewNote(this);
    }

    private void openSelectedNote(NoteEntity note) {
        NoteScreenActivity.openSelectedNote(this, note);
    }

    private void addTestNotes() {
        notesStorage.addNote(new NoteEntity("заголовок 1", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 2", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 3", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 4", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 5", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 6", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 7", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 8", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 9", "съешь ещё этих мягких французских булок, да выпей же чаю"));
        notesStorage.addNote(new NoteEntity("заголовок 10", "съешь ещё этих мягких французских булок, да выпей же чаю"));
    }

}