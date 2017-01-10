package com.vymirs.mykytagumeniuk.dayplanner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nikita on 10/4/2016.
 */
public class Task implements Parcelable {
    public static final String IN_PROGRESS = "In_progress";
    public static final String COMPLETED = "Completed";
    public static final String UNCOMPLETED = "Uncompleted";
    private String name;
    private String date;
    private String time;
    private String description;
    private Status status;

    public enum Status {COMPLETED, IN_PROGRESS, UNCOMPLETED}

    ;

    public Task() {
    }


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Status getStatus() {
        return status;
    }

    public String getStatusToString() {
        String status = "";
        if (this.status == Status.COMPLETED) {
            status = COMPLETED;
        }
        if (this.status == Status.IN_PROGRESS) {
            status = IN_PROGRESS;
        }
        if (this.status == Status.UNCOMPLETED) {
            status = UNCOMPLETED;
        }
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    protected Task(Parcel in) {
        name = in.readString();
        date = in.readString();
        time = in.readString();
        description = in.readString();
        status = Status.valueOf(in.readString());
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
        dest.writeString(time);
        dest.writeString(description);
        dest.writeString(status.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (date != null ? !date.equals(task.date) : task.date != null) return false;
        if (time != null ? !time.equals(task.time) : task.time != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null)
            return false;
        return status == task.status;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
    }

}

