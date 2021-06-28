package com.example.mynotebook.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Objects;

public class Note implements Parcelable {
    private String title;
    private String details;
    private LocalDateTime createDateTime;
    private Boolean current;

    public Note(String title, String details, LocalDateTime createDateTime, Boolean current) {
        this.title = title;
        this.details = details;
        this.createDateTime = createDateTime;
        this.current = current;
    }

    public Note() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Note(Parcel in) {
        title = in.readString();
        details = in.readString();
        createDateTime = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeSerializable(createDateTime);
        dest.writeSerializable(current);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) &&
                Objects.equals(details, note.details) &&
                Objects.equals(createDateTime, note.createDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, details, createDateTime);
    }
}
