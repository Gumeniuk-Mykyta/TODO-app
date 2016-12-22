package com.example.nikita.todo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nikita on 10/4/2016.
 */
public class Task implements Parcelable {
    private String name;
    private String date;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public enum Status {COMPLETED, IN_PROGRESS, UNCOMPLETED}

    ;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task() {
    }

    public Task(String name, String date) {
        this.name = name;
        this.date = date;
    }
//    }

    public String getName() {
        return name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Task(Parcel in) {
        name = in.readString();
        date = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(status.name());
    }
}

