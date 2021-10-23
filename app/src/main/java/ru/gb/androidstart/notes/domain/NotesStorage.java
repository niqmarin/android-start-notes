package ru.gb.androidstart.notes.domain;

import android.os.Parcelable;

import java.util.ArrayList;

public interface NotesStorage {
    ArrayList<NoteEntity> getNotesList();
    void setNotesList(ArrayList<NoteEntity> notesList);
    boolean addNote(NoteEntity note);
    boolean deleteNote(int id);
    boolean editNote(int id, NoteEntity note);
}
