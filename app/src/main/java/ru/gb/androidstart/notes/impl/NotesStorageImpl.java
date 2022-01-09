package ru.gb.androidstart.notes.impl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

import ru.gb.androidstart.notes.domain.NoteEntity;
import ru.gb.androidstart.notes.domain.NotesStorage;

public class NotesStorageImpl implements NotesStorage {
    private ArrayList<NoteEntity> tempNotesList = new ArrayList<>();

    @Override
    public ArrayList<NoteEntity> getNotesList() {
        return new ArrayList<>(tempNotesList);
    }

    @Override
    public void setNotesList(ArrayList<NoteEntity> notesList) {
        this.tempNotesList = notesList;
    }

    @Override
    public boolean addNote(NoteEntity note) {
        if (note.getId() == null) {
            note.setId(UUID.randomUUID().toString());
        }
        tempNotesList.add(note);
        return true;
    }

    @Override
    public boolean deleteNote(String id) {
        for (int i = 0; i < tempNotesList.size(); i++) {
            if (tempNotesList.get(i).getId().equals(id)) {
                tempNotesList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean editNote(String id, NoteEntity note) {
        deleteNote(id);
        tempNotesList.add(note);
        return true;
    }
}

