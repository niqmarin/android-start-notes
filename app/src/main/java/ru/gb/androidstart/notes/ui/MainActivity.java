package ru.gb.androidstart.notes.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import ru.gb.androidstart.notes.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.note_list_fragment_container, new NotesListFragment())
                .commit();
    }
}