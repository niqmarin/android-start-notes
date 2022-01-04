package ru.gb.androidstart.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;

public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller {

    private NoteScreenFragment noteScreenFragment = new NoteScreenFragment();
    private int notesListContainer;
    private int noteScreenContainer;

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
        if (isLandOrientation())
            notesListContainer = R.id.notes_list_fragment_container;
        else
            notesListContainer = R.id.portrait_orientation_fragment_container;
        getSupportFragmentManager()
                .beginTransaction()
                .add(notesListContainer, new NotesListFragment())
                .commit();
    }

    @Override
    public void openNote() {
        if (isLandOrientation())
            noteScreenContainer = R.id.note_screen_fragment_container;
        else
            noteScreenContainer = R.id.portrait_orientation_fragment_container;
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(noteScreenContainer, noteScreenFragment)
                .addToBackStack(null)
                .commit();
    }
}
