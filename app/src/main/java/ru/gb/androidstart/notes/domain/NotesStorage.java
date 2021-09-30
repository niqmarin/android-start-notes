package ru.gb.androidstart.notes.domain;

import java.util.List;

public interface NotesStorage {
    List<NoteEntity> getNotesList();
    boolean addNote(NoteEntity note);
    boolean deleteNote(int id);
    boolean editNote(int id, NoteEntity note);
}
