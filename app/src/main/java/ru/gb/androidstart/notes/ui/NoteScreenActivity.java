package ru.gb.androidstart.notes.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ru.gb.androidstart.notes.R;

public class NoteScreenActivity extends AppCompatActivity {

    private TextView dateTextView;
    private EditText titleEditText;
    private EditText contentsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_screen);

        initViews();
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


}
