package ru.gb.androidstart.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.databinding.FragmentNotesListBinding;
import ru.gb.androidstart.notes.domain.App;
import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;

public class NotesListFragment extends Fragment {

    private NotesStorage notesStorage;
    private NotesAdapter notesAdapter = new NotesAdapter(new OnItemClickListener() {
        @Override
        public void onItemClick(NoteEntity note) {
            fragmentOpenListener.openNote(note);
        }

        @Override
        public void onItemLongClick(View view, NoteEntity note) {
            showNotePopupMenu(view, note);
        }
    });

    private OnFragmentOpenListener fragmentOpenListener;

    private FragmentNotesListBinding binding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentOpenListener = (OnFragmentOpenListener) context;
        }
        catch (ClassCastException e) {
         throw new ClassCastException("Activity must implement OnFragmentSendDataListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesStorage = ((App)requireActivity().getApplication()).getNotesStorage();
        addTestNotes();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotesListBinding.inflate(inflater, container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        ((AppCompatActivity)requireActivity()).setSupportActionBar(binding.notesListToolbar);
        binding.notesListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.notesListRecycleView.setAdapter(notesAdapter);
        notesAdapter.setData(notesStorage.getNotesList());

        binding.addNoteButton.setOnClickListener(v -> fragmentOpenListener.openNote(null));
    }

    private void showNotePopupMenu(View anchorView, NoteEntity note) {
        PopupMenu notePopupMenu = new PopupMenu(requireActivity(), anchorView);
        notePopupMenu.inflate(R.menu.note_popup_menu);
        notePopupMenu.setGravity(Gravity.RIGHT);
        notePopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit_note_popup_menu:
                        fragmentOpenListener.openNote(note);
                        return true;
                    case R.id.delete_note_popup_menu:
                        deleteNote(note);
                        return true;
                    default:
                        return false;
                }
            }
        });
        notePopupMenu.show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.notes_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                fragmentOpenListener.openSettings();
                return true;
            case R.id.info_menu:
                fragmentOpenListener.openInfo();
                return true;
            default:
                super.onOptionsItemSelected(item);
                return false;
        }
    }

    public void saveNoteChange(NoteEntity note) {
        if (note.getId() == null) {
            notesStorage.addNote(note);
        } else {
            notesStorage.editNote(note.getId(), note);
        }
        notesAdapter.setData(notesStorage.getNotesList());
    }

    private void deleteNote(NoteEntity note) {
        notesStorage.deleteNote(note.getId());
        notesAdapter.setData(notesStorage.getNotesList());
    }

    private void addTestNotes() {
        if (notesStorage.getNotesList().size() == 0) {
            notesStorage.addNote(new NoteEntity("заголовок 1", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
            notesStorage.addNote(new NoteEntity("заголовок 2", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
            notesStorage.addNote(new NoteEntity("заголовок 3", "съешь ещё этих мягких французских булок, да выпей же чаю", new Date()));
        }
    }

    interface OnFragmentOpenListener {
        void openNote(@Nullable NoteEntity note);
        void openSettings();
        void openInfo();
    }
}
