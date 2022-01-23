package ru.gb.androidstart.notes.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.databinding.FragmentNoteScreenBinding;
import ru.gb.androidstart.notes.domain.NoteEntity;

public class NoteScreenFragment extends Fragment {
    private NoteEntity currentNote = null;

    private OnFragmentSaveDataListener fragmentSaveDataListener;

    private FragmentNoteScreenBinding binding;

    private static final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("d MMMM yyyy, H:mm", myDateFormatSymbols);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentSaveDataListener = (OnFragmentSaveDataListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OnFragmentSaveDataListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNoteScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        fillNoteData();
    }

    private void initViews(View view) {
        binding.saveNoteButton.setOnClickListener(v -> saveNoteData());
    }

    private void fillNoteData() {
        if (currentNote == null) {
            binding.noteDateTextView.setText(dateTimeFormat.format(new Date()));
            binding.noteTitleTextView.setText("");
            binding.noteContentsTextView.setText("");
        } else {
            binding.noteDateTextView.setText(dateTimeFormat.format(currentNote.getDate()));
            binding.noteTitleTextView.setText(currentNote.getTitle());
            binding.noteContentsTextView.setText(currentNote.getContents());
        }
    }

    public void getNoteData(@Nullable NoteEntity note) {
        currentNote = note;
    }

    private void saveNoteData() {
        if (currentNote == null) {
            currentNote = new NoteEntity(binding.noteTitleTextView.getText().toString(),
                    binding.noteContentsTextView.getText().toString(),
                    new Date()
            );
        } else {
            currentNote.setDate(new Date());
            currentNote.setTitle(binding.noteTitleTextView.getText().toString());
            currentNote.setContents(binding.noteContentsTextView.getText().toString());
        }
        fragmentSaveDataListener.saveNote(currentNote);
    }

    public NoteEntity getCurrentNote() {
        saveNoteData();
        return currentNote;
    }

    interface OnFragmentSaveDataListener {
        void saveNote(NoteEntity note);
    }
}
