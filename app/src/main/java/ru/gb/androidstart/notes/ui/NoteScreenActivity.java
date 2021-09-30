package ru.gb.androidstart.notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.domain.NoteEntity;

public class NoteScreenActivity extends AppCompatActivity {

    private TextView dateTextView;
    private EditText titleEditText;
    private EditText contentsEditText;
    static final String DATE_EXTRA_KEY = "DATE_EXTRA_KEY";
    static final String TITLE_EXTRA_KEY = "TITLE_EXTRA_KEY";
    static final String CONTENTS_EXTRA_KEY = "CONTENTS_EXTRA_KEY";

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
        if (getIntent().getExtras() == null) {
            String currentDate = dateTimeFormat.format(new Date());
            dateTextView.setText(currentDate);
        } else {
            String date = getIntent().getStringExtra(DATE_EXTRA_KEY);
            String title = getIntent().getStringExtra(TITLE_EXTRA_KEY);
            String contents = getIntent().getStringExtra(CONTENTS_EXTRA_KEY);
            dateTextView.setText(date);
            titleEditText.setText(title);
            contentsEditText.setText(contents);
        }
    }

    private void initViews() {
        dateTextView = findViewById(R.id.note_date_text_view);
        titleEditText = findViewById(R.id.note_title_text_view);
        contentsEditText = findViewById(R.id.note_contents_text_view);
    }

    static void openNewNote(Context context) {
        Intent openNewIntent = new Intent(context, NoteScreenActivity.class);
        context.startActivity(openNewIntent);
    }

    static void openSelectedNote(Context context, NoteEntity note) {
        Intent openSelectedIntent = new Intent(context, NoteScreenActivity.class);
        openSelectedIntent.putExtra(DATE_EXTRA_KEY, dateTimeFormat.format(note.getDate()));
        openSelectedIntent.putExtra(TITLE_EXTRA_KEY, note.getTitle());
        openSelectedIntent.putExtra(CONTENTS_EXTRA_KEY, note.getContents());
        context.startActivity(openSelectedIntent);
    }

}
