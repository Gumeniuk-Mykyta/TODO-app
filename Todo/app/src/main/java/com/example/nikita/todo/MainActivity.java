package com.example.nikita.todo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends Activity {
    private ArrayList<Task> tasksList;
    private TasksListAdapter tasksListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
        createTasks();
        fillList();
    }

    private void fillList() {
//        LinearLayout taskLinearLayout = (LinearLayout) findViewById(R.id.items_linear_layout);
        ListView taskListView = (ListView) findViewById(R.id.itemListView);
        tasksListAdapter = new TasksListAdapter(tasksList, this);
        taskListView.setAdapter(tasksListAdapter);

//            taskLinearLayout.addView(taskView);
    }


    private void createTasks() {
        tasksList = new ArrayList<>();
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 200; i++) {
            Task task = new Task();
            task.setName("Task №" + i);
//            task.setDate(calendar.getTimeInMillis() - 100000 * i);
            Log.i("setDate", String.valueOf(task.getDate()));
            tasksList.add(task);
        } 
    }
}
