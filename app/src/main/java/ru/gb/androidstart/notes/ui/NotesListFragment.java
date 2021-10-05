package ru.gb.androidstart.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;
import ru.gb.androidstart.notes.impl.NotesStorageImpl;

public class NotesListFragment extends Fragment {

    private RecyclerView notesRecycleView;
    private NotesStorage notesStorage = new NotesStorageImpl();
    private NotesAdapter notesAdapter = new NotesAdapter();

    private int currentNoteID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTestNotes();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(view.findViewById(R.id.notes_list_toolbar));
        notesRecycleView = view.findViewById(R.id.notes_list_recycle_view);
        notesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notesRecycleView.setAdapter(notesAdapter);
        notesAdapter.setData(notesStorage.getNotesList());
        notesAdapter.setOnItemClickListener(item -> onItemClick(item));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note_menu) {
            currentNoteID = -1;
            openNoteScreen(null);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onItemClick(NoteEntity item) {
        currentNoteID = item.getId();
        openNoteScreen(item);
    }

    private void openNoteScreen(@Nullable NoteEntity note) {
        //todo
    }

    private void addTestNotes() {
        notesStorage.addNote(new NoteEntity("заголовок 1", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        notesStorage.addNote(new NoteEntity("заголовок 2", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        notesStorage.addNote(new NoteEntity("заголовок 3", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
    }
}
