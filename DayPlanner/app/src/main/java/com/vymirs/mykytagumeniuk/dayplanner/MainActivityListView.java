package com.vymirs.mykytagumeniuk.dayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.vymirs.mykytagumeniuk.dayplanner.ActionBar.initActionBar;

public class MainActivityListView extends AppCompatActivity {

    LongTapDialog longTapDialog;
    static public DBHelper db;
    static public DBQueryManager dbQueryManager;
    static public ArrayList<Task> uncompletedTasksList = new ArrayList<>();
    static public ArrayList<Task> completedTasksList = new ArrayList<>();
    static public ArrayList<Task> todayTasksList = new ArrayList<>();
    static public TasksListAdapter tasksListAdapter;
    static public ListView taskListView;
    static public AdapterView.AdapterContextMenuInfo info;
    public static View lineBelowDateOfTask;
    public static View lineBelowTimeOfTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        setContentView(R.layout.listview_main_activity);
        initActionBar(this);
        FABMenuManager.initFabMenu(this);
        checkTodaysTasks();
        setListAdapter(uncompletedTasksList);
        fillList();
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityListView.this.onItemClick((int)id);
            }
        });
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

    public void setListAdapter(ArrayList<Task> tasksList) {
        taskListView = (ListView) findViewById(R.id.itemListView);
        tasksListAdapter = new TasksListAdapter(tasksList, this);
    }

    private void onItemClick(int id) {
        Intent descriptionOfTaskIntent = new Intent(this, DescriptionOfTask.class);
            descriptionOfTaskIntent.putExtra("id", id);
        if (ActionBar.isAllTasksTabSelected) {
            DescriptionOfTask.tasksList = uncompletedTasksList;
        }
        if (ActionBar.isCompletedTabSelected) {
            DescriptionOfTask.tasksList = completedTasksList;
        }
        if (ActionBar.isTodaysTabSelected) {
            DescriptionOfTask.tasksList = todayTasksList;
        }
        this.startActivity(descriptionOfTaskIntent);
    }
    private void putExtrasToIntent(Intent intent,Task task, ArrayList<Task> tasksList){
        intent.putExtra("Task", task);
        intent.putParcelableArrayListExtra("Tasks list", tasksList);
    }

    private boolean onLongListItemClick(View view, int position, long id) {
        longTapDialog = new LongTapDialog();
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

    public void onClick(View v) {
        DialogsManager.showCalendarDialog(this);
    }

    public void onFabButtonClick(View view) {
        FABMenuManager.onFABButtonClick(view, this);
    }

}

