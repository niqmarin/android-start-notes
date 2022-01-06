package ru.gb.androidstart.notes.ui;

import android.content.Context;
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

    private RecyclerView notesRecyclerView;
    private NotesStorage notesStorage = new NotesStorageImpl();
    private NotesAdapter notesAdapter = new NotesAdapter();
    private FragmentManager fragmentManager;

    private OnFragmentSendDataListener fragmentSendDataListener;

    private Date newNoteDate;
    private String newNoteTitle;
    private String newNoteContents;

    private String currentNoteID;

    private static final String NOTES_LIST_KEY = "NOTES_LIST_KEY";
    private static final String NOTE_STORAGE_KEY = "NOTE_STORAGE_KEY";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        }
        catch (ClassCastException e) {
         throw new ClassCastException("Activity must implement OnFragmentSendDataListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = requireActivity().getSupportFragmentManager();
        addTestNotes();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restoreNotesList();
        initViews(view);
        getNoteData();
    }

    private void initViews(View view) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(view.findViewById(R.id.notes_list_toolbar));
        notesRecyclerView = view.findViewById(R.id.notes_list_recycle_view);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.setData(notesStorage.getNotesList());
        notesAdapter.setOnItemClickListener(note -> onItemClick(note));
    }

    private void getNoteData() {
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

    private void restoreNotesList() {
        fragmentManager.setFragmentResultListener(NOTE_STORAGE_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                notesStorage.setNotesList(result.getParcelableArrayList(NOTES_LIST_KEY));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle result = new Bundle();
        result.putParcelableArrayList(NOTES_LIST_KEY, notesStorage.getNotesList());
        fragmentManager.setFragmentResult(NOTE_STORAGE_KEY, result);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.notes_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note_menu) {
            currentNoteID = null;
            fragmentSendDataListener.openNote(null);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onItemClick(@NonNull NoteEntity note) {
        currentNoteID = note.getId();
        fragmentSendDataListener.openNote(note);
    }

    private void saveNoteChange() {
        if (currentNoteID == null) {
            NoteEntity newNote = new NoteEntity(newNoteTitle, newNoteContents, newNoteDate);
            notesStorage.addNote(newNote);
        } else {
            NoteEntity newNote = new NoteEntity(currentNoteID, newNoteTitle, newNoteContents, newNoteDate);
            notesStorage.editNote(currentNoteID, newNote);
        }
        notesAdapter.setData(notesStorage.getNotesList());
    }

    private void addTestNotes() {
        if (notesStorage.getNotesList().size() == 0) {
            notesStorage.addNote(new NoteEntity("заголовок 1", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
            notesStorage.addNote(new NoteEntity("заголовок 2", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
            notesStorage.addNote(new NoteEntity("заголовок 3", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        }
    }

    private OnFragmentSendDataListener getFragmentSendDataListener() {
        return (OnFragmentSendDataListener) requireActivity();
    }

    interface OnFragmentSendDataListener {
        void openNote(@Nullable NoteEntity note);
    }
}
