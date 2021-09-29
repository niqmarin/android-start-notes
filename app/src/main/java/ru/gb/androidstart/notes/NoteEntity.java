package ru.gb.androidstart.notes;

import java.util.Date;

public class NoteEntity {
    private final int id;
    private String title;
    private String contents;
    private Date date;

    public NoteEntity(int id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.date = new Date();
    }

    public int getId() {
        return id;
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

