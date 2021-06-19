package com.example.mynotebook;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class MyNote implements Parcelable {
    private String title;
    private String details;
    private LocalDateTime createDateTime;

    public MyNote(String title, String details, LocalDateTime createDateTime) {
        this.title = title;
        this.details = details;
        this.createDateTime = createDateTime;
    }

    public MyNote() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected MyNote(Parcel in) {
        title = in.readString();
        details = in.readString();
        createDateTime = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        @Override
        public MyNote[] newArray(int size) {
            return new MyNote[size];
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
    }
}
