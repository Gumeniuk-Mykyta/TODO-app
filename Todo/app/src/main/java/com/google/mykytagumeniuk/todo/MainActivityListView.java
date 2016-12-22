package com.google.mykytagumeniuk.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.google.mykytagumeniuk.todo.ActionBar.initActionBar;
import static com.google.mykytagumeniuk.todo.DialogsManager.showCalendarDialog;
import static com.google.mykytagumeniuk.todo.FABMenuManager.initFabMenu;
import static com.google.mykytagumeniuk.todo.FABMenuManager.onFABButtonClick;

public class MainActivityListView extends AppCompatActivity {

    LongTapDialog longTapDialog;
    static public DBHelper db;
    static public DBQueryManager dbQueryManager;
    static public ArrayList<Task> uncompletedTasksList = new ArrayList<>();
    static public ArrayList<Task> completedTasksList = new ArrayList<>();
    static public ArrayList<Task> todayTasksList = new ArrayList<>();
    static private boolean wasLaunched = false;
    static public TasksListAdapter tasksListAdapter;
    static public ListView taskListView;
    static public String nameOfTask;
    static public boolean isTaskBeingEdited;
    static public AdapterView.AdapterContextMenuInfo info;
    public static View lineBelowDateOfTask;
    public static View lineBelowTimeOfTask;
//    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        setContentView(R.layout.listview_main_beta);
        initActionBar(this);
        initFabMenu(this);
        if (wasLaunched == false) {
            createUncompletedTasks();
            createCompletedTasks();
            createTodaysTasks();
        }
        checkTodaysTasks();
        wasLaunched = true;
        setListAdapter(uncompletedTasksList);
        fillList();
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return onLongListItemClick(view, position, id);
            }
        });
        db = new DBHelper(this);
        dbQueryManager = new DBQueryManager(db, this);


        dbQueryManager.readTasksFromDB();
    }

    private void checkTodaysTasks() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String currentDate = format.format(calendar.getTime());
        for (Task task : uncompletedTasksList) {
            if (task.getDate().equals(currentDate)) {
                todayTasksList.add(task);
            }
        }
    }

    //    public void setListAdapter() {
    public void setListAdapter(ArrayList<Task> tasksList) {
        taskListView = (ListView) findViewById(R.id.itemListView);
        tasksListAdapter = new TasksListAdapter(tasksList, this);
    }

    private boolean onLongListItemClick(View view, int position, long id) {
        longTapDialog = new LongTapDialog();
        Toast.makeText(this, "" + String.valueOf(position) + "id " + String.valueOf(id), Toast.LENGTH_SHORT).show();
        if (ActionBar.isAllTasksTabSelected) {
            longTapDialog.showLongTapDialog(this, (int) id, uncompletedTasksList);
        }
        if (ActionBar.isCompletedTabSelected) {
            longTapDialog.showLongTapDialog(this, (int) id, completedTasksList);
        }
        if (ActionBar.isTodaysTabSelected) {
            longTapDialog.showLongTapDialog(this, (int) id, todayTasksList);
        }
        return true;
    }


    public static void fillList() {
        taskListView.setAdapter(tasksListAdapter);
    }


    private void createUncompletedTasks() {
        int year;
        int month;
        int dayOfMonth;
        int hour;
        int minute;
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
            year = random.nextInt(6) + 2000;
            month = random.nextInt(12) + 1;
            dayOfMonth = random.nextInt(30) + 1;
            hour = random.nextInt(23) + 1;
            minute = random.nextInt(59) + 1;
            calendar.set(year, month, dayOfMonth);
            Task task = new Task();
            task.setName("Task №" + i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String date = format.format(calendar.getTime());
            task.setDate(date);
            task.setTime(hour + ":" + minute);
            Log.i("setDate", task.getDate());
            task.setStatus(Task.Status.UNCOMPLETED);
//            if (i % 2 == 0) {
//                task.setStatus(Task.Status.COMPLETED);
//            }
            if (i % 3 == 0) {
                task.setStatus(Task.Status.IN_PROGRESS);
            }
            uncompletedTasksList.add(task);
            Log.i("taskList size :", String.valueOf(uncompletedTasksList.size()));
//            setTasksList(uncompletedTasksList);
        }
    }

    private void createTodaysTasks() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setName("todays task №" + i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String date = format.format(calendar.getTime());
            task.setDate(date);
            task.setStatus(Task.Status.UNCOMPLETED);
            uncompletedTasksList.add(task);
        }
    }

    private void createCompletedTasks() {
        int year;
        int month;
        int dayOfMonth;
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 4; i++) {
            year = random.nextInt(6) + 2000;
            month = random.nextInt(12) + 1;
            dayOfMonth = random.nextInt(30) + 1;
            calendar.set(year, month, dayOfMonth);
            Task task = new Task();
            task.setName("Task №" + i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String date = format.format(calendar.getTime());
            task.setDate(date);
            Log.i("setDate", task.getDate());
//            task.setStatus(Task.Status.UNCOMPLETED);
//            if (i % 2 == 0) {
            task.setStatus(Task.Status.COMPLETED);
//            }
//            if (i % 3 == 0) {
//                task.setStatus(Task.Status.IN_PROGRESS);
//            }
            completedTasksList.add(task);
            Log.i("taskList size :", String.valueOf(uncompletedTasksList.size()));
//            setTasksList(uncompletedTasksList);
        }
    }

    public void onClick(View v) {
        showCalendarDialog(this);
    }


    public void onFabButtonClick(View view) {
        onFABButtonClick(view, this);
    }

}

