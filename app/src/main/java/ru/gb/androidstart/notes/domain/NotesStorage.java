package ru.gb.androidstart.notes.domain;

import java.util.ArrayList;

public interface NotesStorage {
    ArrayList<NoteEntity> getNotesList();
    boolean addNote(NoteEntity note);
    boolean deleteNote(int id);
    boolean editNote(int id, NoteEntity note);
}
