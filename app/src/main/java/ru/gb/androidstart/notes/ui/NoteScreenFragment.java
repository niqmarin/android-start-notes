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
import ru.gb.androidstart.notes.domain.NoteEntity;

public class NoteScreenFragment extends Fragment {
    private ImageButton saveNoteButton;
    private TextView dateTextView;
    private EditText titleEditText;
    private EditText contentsEditText;

    private NoteEntity currentNote = null;

    private OnFragmentSaveDataListener fragmentSaveDataListener;

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
        return inflater.inflate(R.layout.fragment_note_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        fillNoteData();
    }

    private void initViews(View view) {
        saveNoteButton = view.findViewById(R.id.save_note_button);
        saveNoteButton.setOnClickListener(v -> saveNoteData());
        dateTextView = view.findViewById(R.id.note_date_text_view);
        titleEditText = view.findViewById(R.id.note_title_text_view);
        contentsEditText = view.findViewById(R.id.note_contents_text_view);
    }

    private void fillNoteData() {
        if (currentNote == null) {
            dateTextView.setText(dateTimeFormat.format(new Date()));
            titleEditText.setText("");
            contentsEditText.setText("");
        } else {
            dateTextView.setText(dateTimeFormat.format(currentNote.getDate()));
            titleEditText.setText(currentNote.getTitle());
            contentsEditText.setText(currentNote.getContents());
        }
    }

    public void getNoteData(@Nullable NoteEntity note) {
        currentNote = note;
    }

    private void saveNoteData() {
        if (currentNote == null) {
            currentNote = new NoteEntity(titleEditText.getText().toString(),
                    contentsEditText.getText().toString(),
                    new Date()
            );
        } else {
            currentNote.setDate(new Date());
            currentNote.setTitle(titleEditText.getText().toString());
            currentNote.setContents(contentsEditText.getText().toString());
        }
        fragmentSaveDataListener.saveNote(currentNote);
    }

    interface OnFragmentSaveDataListener {
        void saveNote(NoteEntity note);
    }
}
