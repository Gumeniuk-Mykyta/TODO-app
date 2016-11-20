package com.example.nikita.todo;

/**
 * Created by Nikita on 10/4/2016.
 */
public class Task {
    private String name;
    private String date;
    public enum Status{COMPLETED, IN_PROCESS,UNCOMPLETED};
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
}
