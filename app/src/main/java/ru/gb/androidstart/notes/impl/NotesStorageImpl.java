package ru.gb.androidstart.notes.impl;

import java.util.ArrayList;

import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;

public class NotesStorageImpl implements NotesStorage {
    private ArrayList<NoteEntity> tempNotesList = new ArrayList<>();

    @Override
    public ArrayList<NoteEntity> getNotesList() {
        return new ArrayList<>(tempNotesList);
    }

    @Override
    public boolean addNote(NoteEntity note) {
        tempNotesList.add(note);
        return true;
    }

    @Override
    public boolean deleteNote(int id) {
        for (int i = 0; i < tempNotesList.size(); i++) {
            if (tempNotesList.get(i).getId() == id) {
                tempNotesList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean editNote(int id, NoteEntity note) {
        deleteNote(id);
        tempNotesList.add(note);
        return true;
    }
}
