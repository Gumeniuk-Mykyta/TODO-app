package com.example.nikita.todo;

import android.os.AsyncTask;
import android.widget.ListView;

import java.util.List;

import static com.example.nikita.todo.MainActivityListView.taskListView;

/**
 * Created by Nikita on 11/2/2016.
 */

public class MyTask extends AsyncTask {
    //    MainActivityListView mainActivityListView = new MainActivityListView();
    ListView taskListView;
    TasksListAdapter tasksListAdapter;

    public MyTask(ListView taskListView, TasksListAdapter tasksListAdapter) {
        this.taskListView = taskListView;
        this.tasksListAdapter = tasksListAdapter;
    }

//    public MyTask(){
//
//    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        taskListView.setAdapter(tasksListAdapter);
        super.onPostExecute(o);
    }

}
