package ru.gb.androidstart.notes.domain;

import java.util.Date;

public class NoteEntity {

    private int id;
    private String title;
    private String contents;
    private Date date;
    private static int counter = 0;

    public NoteEntity(String title, String contents) {
        this.id = ++counter;
        this.title = title;
        this.contents = contents;
        this.date = new Date();
    }

    public NoteEntity(int id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.date = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

