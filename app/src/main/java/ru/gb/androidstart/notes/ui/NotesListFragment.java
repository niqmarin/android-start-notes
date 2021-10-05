package ru.gb.androidstart.notes.ui;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
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
    FragmentManager fragmentManager;
    private Date newNoteDate;
    private String newNoteTitle;
    private String newNoteContents;

    private int currentNoteID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
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

        fragmentManager.setFragmentResultListener(NoteScreenFragment.NOTE_DATA_OUT_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                newNoteDate = new Date(result.getLong(NoteScreenFragment.DATE_KEY, -1));
                newNoteTitle = result.getString(NoteScreenFragment.TITLE_KEY);
                newNoteContents = result.getString(NoteScreenFragment.CONTENTS_KEY);
                saveNoteChange();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
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
        @Nullable
        Bundle result = new Bundle();
        if (note != null) {
            result.putLong(NoteScreenFragment.DATE_KEY, note.getDate().getTime());
            result.putString(NoteScreenFragment.TITLE_KEY, note.getTitle());
            result.putString(NoteScreenFragment.CONTENTS_KEY, note.getContents());
        } else {
            result.putLong(NoteScreenFragment.DATE_KEY, new Date().getTime());
            result.putString(NoteScreenFragment.TITLE_KEY, "");
            result.putString(NoteScreenFragment.CONTENTS_KEY, "");
        }
        fragmentManager.setFragmentResult(NoteScreenFragment.NOTE_DATA_IN_KEY, result);
        fragmentManager
                .beginTransaction()
                .add(R.id.portrait_orientation_fragment_container, new NoteScreenFragment())
                .addToBackStack(null)
                .commit();
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
