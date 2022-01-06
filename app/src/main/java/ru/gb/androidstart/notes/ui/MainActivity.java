package ru.gb.androidstart.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnFragmentSendDataListener, NoteScreenFragment.OnFragmentSaveDataListener {

    private NotesListFragment notesListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openNotesList();
    }

    private boolean isLandOrientation(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void openNotesList() {
        notesListFragment = new NotesListFragment();
        int notesListContainer;
        if (isLandOrientation())
            notesListContainer = R.id.notes_list_fragment_container;
        else
            notesListContainer = R.id.portrait_orientation_fragment_container;
        getSupportFragmentManager()
                .beginTransaction()
                .add(notesListContainer, notesListFragment)
                .commit();
    }

    @Override
    public void openNote(@Nullable NoteEntity note) {
        NoteScreenFragment noteScreenFragment = new NoteScreenFragment();
        int noteScreenContainer;
        if (isLandOrientation())
            noteScreenContainer = R.id.note_screen_fragment_container;
        else
            noteScreenContainer = R.id.portrait_orientation_fragment_container;
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(noteScreenContainer, noteScreenFragment)
                .addToBackStack(null)
                .commit();
        noteScreenFragment.getNoteData(note);
    }

    @Override
    public void saveNote(@NonNull NoteEntity note) {
        notesListFragment.saveNoteChange(note);
    }
}
