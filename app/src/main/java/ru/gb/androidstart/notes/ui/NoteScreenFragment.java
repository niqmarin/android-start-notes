package ru.gb.androidstart.notes.ui;

import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentResultListener;
import ru.gb.androidstart.notes.R;

public class NoteScreenFragment extends Fragment {
    private ImageButton saveNoteButton;
    private TextView dateTextView;
    private EditText titleEditText;
    private EditText contentsEditText;
    static final String DATE_KEY = "DATE_KEY";
    static final String TITLE_KEY = "TITLE_KEY";
    static final String CONTENTS_KEY = "CONTENTS_KEY";
    static final String NOTE_DATA_KEY = "NOTE_DATA_KEY";
    Date currentDate = new Date();

    private static final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("d MMMM yyyy, H:mm", myDateFormatSymbols);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateTextView = view.findViewById(R.id.note_date_text_view);
        titleEditText = view.findViewById(R.id.note_title_text_view);
        contentsEditText = view.findViewById(R.id.note_contents_text_view);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.setFragmentResultListener(NOTE_DATA_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                currentDate.setTime(result.getLong(DATE_KEY, -1));
                dateTextView.setText(dateTimeFormat.format(currentDate));
                titleEditText.setText(result.getString(TITLE_KEY));
                contentsEditText.setText(result.getString(CONTENTS_KEY));
            }
        });
    }
}
