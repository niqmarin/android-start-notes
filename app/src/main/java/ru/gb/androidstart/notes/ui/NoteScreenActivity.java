package ru.gb.androidstart.notes.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;

public class NoteScreenActivity extends AppCompatActivity {

    private ImageButton saveNoteButton;
    private TextView dateTextView;
    private EditText titleEditText;
    private EditText contentsEditText;
    static final String DATE_EXTRA_KEY = "DATE_EXTRA_KEY";
    static final String TITLE_EXTRA_KEY = "TITLE_EXTRA_KEY";
    static final String CONTENTS_EXTRA_KEY = "CONTENTS_EXTRA_KEY";
    Date currentDate;

    private static final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("d MMMM yyyy, H:mm", myDateFormatSymbols);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_screen);

        initViews();
        fillViews();
    }

    private void fillViews() {
        currentDate = new Date();
        if (getIntent().getExtras() == null) {
            dateTextView.setText(dateTimeFormat.format(currentDate));
        } else {
            currentDate.setTime(getIntent().getLongExtra(DATE_EXTRA_KEY, -1));
            dateTextView.setText(dateTimeFormat.format(currentDate));
            titleEditText.setText(getIntent().getStringExtra(TITLE_EXTRA_KEY));
            contentsEditText.setText(getIntent().getStringExtra(CONTENTS_EXTRA_KEY));
        }
    }

    private void initViews() {
        saveNoteButton = findViewById(R.id.save_note_button);
        saveNoteButton.setOnClickListener(v -> saveNote());
        dateTextView = findViewById(R.id.note_date_text_view);
        titleEditText = findViewById(R.id.note_title_text_view);
        contentsEditText = findViewById(R.id.note_contents_text_view);
        contentsEditText.requestFocus();
    }

    private void saveNote() {
        currentDate = new Date();
        Intent noteChangeIntent = new Intent();
        noteChangeIntent.putExtra(DATE_EXTRA_KEY, currentDate.getTime());
        noteChangeIntent.putExtra(TITLE_EXTRA_KEY, titleEditText.getText().toString());
        noteChangeIntent.putExtra(CONTENTS_EXTRA_KEY, contentsEditText.getText().toString());
        setResult(Activity.RESULT_OK, noteChangeIntent);
    }

}
