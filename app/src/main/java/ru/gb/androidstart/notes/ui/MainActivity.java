package ru.gb.androidstart.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isLandOrientation())
            initLandNotesViewFragments();
        else
            initNotesListFragment();
    }

    private void initNotesListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.portrait_orientation_fragment_container, new NotesListFragment())
                .commit();
    }

    private void initLandNotesViewFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.notes_list_fragment_container, new NotesListFragment())
                .add(R.id.note_screen_fragment_container, new NoteScreenFragment())
                .commit();
    }

    private boolean isLandOrientation(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
