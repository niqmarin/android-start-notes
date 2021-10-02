package ru.gb.androidstart.notes.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;
import ru.gb.androidstart.notes.impl.NotesStorageImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import static androidx.activity.result.contract.ActivityResultContracts.*;

public class NotesListActivity extends AppCompatActivity {
    private FloatingActionButton addNoteButton;
    private RecyclerView notesRecycleView;
    private NotesStorage notesStorage = new NotesStorageImpl();
    private NotesAdapter notesAdapter = new NotesAdapter();
    private ActivityResultLauncher<Intent> noteScreenResultLauncher;
    private int currentNoteID;
    private Date newNoteDate;
    private String newNoteTitle;
    private String newNoteContents;
    private Intent noteChangeIntent;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        addTestNotes();

        getNoteScreenResult();

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
        addNoteButton.setOnClickListener(v -> {
            currentNoteID = -1;
            openNoteScreen(null);
        });
        notesAdapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(NoteEntity item) {
        currentNoteID = item.getId();
        openNoteScreen(item);
    }

    private void openNoteScreen(@Nullable NoteEntity note) {
        Intent openNoteScreen = new Intent(this, NoteScreenActivity.class);
        if (note != null) {
            openNoteScreen.putExtra(NoteScreenActivity.DATE_EXTRA_KEY, note.getDate().getTime());
            openNoteScreen.putExtra(NoteScreenActivity.TITLE_EXTRA_KEY, note.getTitle());
            openNoteScreen.putExtra(NoteScreenActivity.CONTENTS_EXTRA_KEY, note.getContents());
        }
        noteScreenResultLauncher.launch(openNoteScreen);
    }

    private void getNoteScreenResult() {
        noteScreenResultLauncher = registerForActivityResult(new StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                noteChangeIntent = result.getData();
                if (noteChangeIntent != null) {
                    newNoteDate = new Date(noteChangeIntent.getLongExtra(NoteScreenActivity.DATE_EXTRA_KEY, -1));
                    newNoteTitle = noteChangeIntent.getStringExtra(NoteScreenActivity.TITLE_EXTRA_KEY);
                    newNoteContents = noteChangeIntent.getStringExtra(NoteScreenActivity.CONTENTS_EXTRA_KEY);
                    saveNoteChange();
                }
            }
        });
    }

    private void saveNoteChange() {
        if (currentNoteID == -1) {
            NoteEntity newNote = new NoteEntity(newNoteTitle, newNoteContents, newNoteDate);
            notesStorage.addNote(newNote);
        } else {
            NoteEntity newNote = new NoteEntity(currentNoteID, newNoteTitle, newNoteContents, newNoteDate);
            notesStorage.editNote(currentNoteID, newNote);
        }
        notesAdapter.setData(notesStorage.getNotesList());
    }

    private void addTestNotes() {
        notesStorage.addNote(new NoteEntity("заголовок 1", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        notesStorage.addNote(new NoteEntity("заголовок 2", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        notesStorage.addNote(new NoteEntity("заголовок 3", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
    }

}