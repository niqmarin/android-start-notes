package ru.gb.androidstart.notes.ui;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import ru.gb.androidstart.notes.R;
import ru.gb.androidstart.notes.databinding.ActivityMainBinding;
import ru.gb.androidstart.notes.domain.NoteEntity;

public class MainActivity extends AppCompatActivity implements NotesListFragment.OnFragmentOpenListener, NoteScreenFragment.OnFragmentSaveDataListener {

    private NotesListFragment notesListFragment;

    private NoteScreenFragment currentNoteScreenFragment;

    private static final String NOTE_SCREEN_FRAGMENT_TAG = "NOTE_SCREEN_FRAGMENT_TAG";
    private static final String SETTINGS_FRAGMENT_TAG = "SETTINGS_FRAGMENT_TAG";
    private static final String INFO_FRAGMENT_TAG = "INFO_FRAGMENT_TAG";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        openNotesList();
    }

    private boolean isLandOrientation(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void openNotesList() {
        notesListFragment = new NotesListFragment();
        int notesListContainer;
        if (isLandOrientation())
            notesListContainer = binding.notesListFragmentContainer.getId();
        else
            notesListContainer = binding.portraitOrientationFragmentContainer.getId();

        getSupportFragmentManager()
                .beginTransaction()
                .add(notesListContainer, notesListFragment)
                .commit();
    }

    @Override
    public void openNote(@Nullable NoteEntity note) {
        NoteScreenFragment noteScreenFragment = new NoteScreenFragment();
        int noteScreenContainer;
        if (isLandOrientation())
            noteScreenContainer = binding.noteScreenFragmentContainer.getId();
        else
            noteScreenContainer = binding.portraitOrientationFragmentContainer.getId();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(noteScreenContainer, noteScreenFragment, NOTE_SCREEN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
        noteScreenFragment.getNoteData(note);
    }

    @Override
    public void openSettings() {
        int settingsScreenContainer;
        if (isLandOrientation())
            settingsScreenContainer = binding.menuFragmentContainer.getId();
        else
            settingsScreenContainer = binding.portraitOrientationFragmentContainer.getId();

        getSupportFragmentManager()
                .beginTransaction()
                .add(settingsScreenContainer, new SettingsFragment(), SETTINGS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openInfo() {
        int infoScreenContainer;
        if (isLandOrientation())
            infoScreenContainer = binding.menuFragmentContainer.getId();
        else
            infoScreenContainer = binding.portraitOrientationFragmentContainer.getId();

        getSupportFragmentManager()
                .beginTransaction()
                .add(infoScreenContainer, new InfoFragment(), INFO_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void saveNote(@NonNull NoteEntity note) {
        notesListFragment.saveNoteChange(note);
    }

    @Override
    public void onBackPressed() {
        currentNoteScreenFragment = (NoteScreenFragment)getSupportFragmentManager().findFragmentByTag(NOTE_SCREEN_FRAGMENT_TAG);
        Fragment settingsFragment = getSupportFragmentManager().findFragmentByTag(SETTINGS_FRAGMENT_TAG);
        Fragment infoFragment = getSupportFragmentManager().findFragmentByTag(INFO_FRAGMENT_TAG);

        if (isLandOrientation()) {
            if (settingsFragment == null && infoFragment == null && currentNoteScreenFragment != null) {
                showAlertDialog();
            } else {
                super.onBackPressed();
            }
        } else {
            if (currentNoteScreenFragment != null && currentNoteScreenFragment.isVisible()) {
                showAlertDialog();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_save_dialog_24)
                .setTitle(" ")
                .setMessage(R.string.save_message_dialog)
                .setPositiveButton(R.string.positive_button_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveNote(currentNoteScreenFragment.getCurrentNote());
                        getSupportFragmentManager().popBackStack();
                    }
                })
                .setNegativeButton(R.string.negative_button_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSupportFragmentManager().popBackStack();
                    }
                })
                .show();

    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, R.string.goodbye_message, Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
