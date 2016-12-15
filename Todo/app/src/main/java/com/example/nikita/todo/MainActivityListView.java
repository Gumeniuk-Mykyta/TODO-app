package com.example.nikita.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.example.nikita.todo.ActionBar.initActionBar;
import static com.example.nikita.todo.DialogsManager.openEditTaskDialog;
import static com.example.nikita.todo.DialogsManager.showCalendarDialog;
import static com.example.nikita.todo.FABMenuManager.initFabMenu;
import static com.example.nikita.todo.FABMenuManager.onFABButtonClick;

public class MainActivityListView extends AppCompatActivity {

    LongTapDialog longTapDialog;
    static public ArrayList<Task> tasksList = new ArrayList<>();
    static private boolean wasLaunched = false;
    static public TasksListAdapter tasksListAdapter;
    static public ListView taskListView;
    static public String nameOfTask;
    static public boolean isTaskBeingEdited;
    static public AdapterView.AdapterContextMenuInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
        initActionBar(this);
        initFabMenu(this);
        if (wasLaunched == false) {
            createTasks();
        }
        taskListView = (ListView) findViewById(R.id.itemListView);
        tasksListAdapter = new TasksListAdapter(tasksList, this);
        Log.i("taskList size :", String.valueOf(tasksList.size()));
        fillList();
        Log.i("taskList size :", String.valueOf(tasksList.size()));
        registerForContextMenu(findViewById(R.id.itemListView));
        wasLaunched = true;
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return onLongListItemClick(view, position, id);
            }
        });

    }

    private boolean onLongListItemClick(View view, int position, long id) {
        longTapDialog = new LongTapDialog();
        Toast.makeText(this, "" + String.valueOf(position) + "id " + String.valueOf(id), Toast.LENGTH_SHORT).show();
        longTapDialog.showLongTapDialog(this, (int) id);
        return true;
    }


    public static void fillList() {
        taskListView.setAdapter(tasksListAdapter);
    }


    private void createTasks() {
        int year;
        int month;
        int dayOfMonth;
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 30; i++) {
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
            task.setStatus(Task.Status.UNCOMPLETED);
            if (i % 2 == 0) {
                task.setStatus(Task.Status.COMPLETED);
            }
            if (i % 3 == 0) {
                task.setStatus(Task.Status.IN_PROCESS);
            }
            tasksList.add(task);
            Log.i("taskList size :", String.valueOf(tasksList.size()));
//            setTasksList(tasksList);
        }
    }

    public void onClick(View v) {
        showCalendarDialog(this);
    }


    public void onFabButtonClick(View view) {
        onFABButtonClick(view, this);
    }

}

