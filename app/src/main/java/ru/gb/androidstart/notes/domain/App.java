package ru.gb.androidstart.notes.domain;

import android.app.Application;

import ru.gb.androidstart.notes.impl.NotesStorageImpl;

public class App extends Application {

    private NotesStorage notesStorage = new NotesStorageImpl();

    public NotesStorage getNotesStorage () {
        return notesStorage;
    }

}