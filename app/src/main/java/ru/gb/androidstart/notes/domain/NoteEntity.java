package ru.gb.androidstart.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteEntity implements Parcelable {

    private String id;
    private String title;
    private String contents;
    private Date date;

    public NoteEntity(String title, String contents, Date date) {
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public NoteEntity(String id, String title, String contents, Date date) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        contents = in.readString();
        date = new Date();
        date.setTime(in.readLong());
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(contents);
        parcel.writeLong(date.getTime());
    }
}

