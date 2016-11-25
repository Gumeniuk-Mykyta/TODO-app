package com.example.nikita.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivityListView extends AppCompatActivity {
    TextView TVSortBy;
    TextView TVAddANewTask;
    FloatingActionButton fabMain;
    FloatingActionButton fabSecond;
    FloatingActionButton fabThird;
    Animation fabOpen;
    Animation fabClose;
    Animation rotateClockWise;
    Animation rotateAnticlockwise;
    boolean isFabMenuOpen = false;
    static private ArrayList<Task> tasksList = new ArrayList<>();
    static private boolean wasLaunched = false;
    static public TasksListAdapter tasksListAdapter;
    static public ListView taskListView;
    static public String nameOfTask;
    static public boolean isTaskBeingEdited;
    public ArrayList<Task> getTasksList() {
        return tasksList;
    }
    public void setTasksList(ArrayList<Task> tasksList) {
        this.tasksList = tasksList;
    }
    static public AdapterView.AdapterContextMenuInfo info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main);
        initActionBar();
        initFabMenu();
        if (wasLaunched == false) {
            createTasks();
        }
        taskListView = (ListView) findViewById(R.id.itemListView);
        tasksListAdapter = new TasksListAdapter(getTasksList(), this);
        Log.i("taskList size :", String.valueOf(tasksList.size()));
        fillList();
        Log.i("taskList size :", String.valueOf(tasksList.size()));
        registerForContextMenu(findViewById(R.id.itemListView));
        wasLaunched = true;
    }

    private void initFabMenu(){
        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabSecond = (FloatingActionButton) findViewById(R.id.fab_second);
        fabThird = (FloatingActionButton) findViewById(R.id.fab_third);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotateAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        rotateClockWise= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        TVSortBy = (TextView) findViewById(R.id.tv_sort_by);
        TVAddANewTask = (TextView) findViewById(R.id.tv_add_a_new_task);
    }

    private void initActionBar() {
       ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab()
        .setText("All tasks")
        .setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Toast.makeText(getApplicationContext(),"all tasks tab was selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        }));
        actionBar.addTab(actionBar.newTab()
        .setText("Today's tasks")
        .setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Toast.makeText(getApplicationContext(),"today's tasks tab was selected",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        }));
        actionBar.addTab(actionBar.newTab()
        .setText("Completed")
        .setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        }));

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
            setTasksList(tasksList);
        }
    }

    public void onAddTaskButtonClick(View view) {
        final EditText ETNameOfTask = new EditText(this);
        new AlertDialog.Builder(this)
                .setMessage("Enter the name of a task")
                .setView(ETNameOfTask)
                .setPositiveButton("Choose Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameOfTaskText = String.valueOf(ETNameOfTask.getText());
                        nameOfTask = nameOfTaskText;
                        isTaskBeingEdited = false;
                        showDatePickerDialog();
                    }
                })
                .create()
                .show();
    }


    private void showDatePickerDialog() {
        DialogFragment datePickerDialogFragment = new DatePickerDialog();
        datePickerDialogFragment.show(getFragmentManager(), "DatePickerDialog");
    }

    public void onFabButtonClick(View view) {
        switch (view.getId()) {
            case R.id.fab_main:
                if (isFabMenuOpen){
                    ViewCompat.animate(fabMain).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
                    fabSecond.startAnimation(fabClose);
                    fabThird.startAnimation(fabClose);
                    TVAddANewTask.startAnimation(fabClose);
                    TVSortBy.startAnimation(fabClose);
                    fabSecond.setClickable(false);
                    fabThird.setClickable(false);
                    TVAddANewTask.setClickable(false);
                    TVSortBy.setClickable(false);
                    isFabMenuOpen = false;
                }else{
                    ViewCompat.animate(fabMain).rotation(90.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
                    fabSecond.startAnimation(fabOpen);
                    fabThird.startAnimation(fabOpen);
                    TVAddANewTask.startAnimation(fabOpen);
                    TVSortBy.startAnimation(fabOpen);
                    fabSecond.setClickable(true);
                    fabThird.setClickable(true);
                    TVAddANewTask.setClickable(true);
                    TVSortBy.setClickable(true);
                    isFabMenuOpen = true;
                }
                break;
        }
    }


    public static class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new android.app.DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            this.year = year;
            this.month = month;
            this.dayOfMonth = dayOfMonth;
            Log.i("dateOfTask", "year " + this.year + "month :" + this.month + "dayOfMonth" + this.dayOfMonth);
            calendar.set(year, month, dayOfMonth);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String taskDate = format.format(calendar.getTime());
            if (isTaskBeingEdited) {
                tasksList.get(info.position).setDate(taskDate);
                tasksList.get(info.position).setName(nameOfTask);
                tasksListAdapter.notifyDataSetChanged();
            } else {
                tasksList.add(new Task(nameOfTask, taskDate));
            }
            Log.i("taskList size :", String.valueOf(tasksList.size()));
            fillList();
        }
    }

    public static class EditDatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new android.app.DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            this.year = year;
            this.month = month;
            this.dayOfMonth = dayOfMonth;
            Log.i("dateOfTask", "year " + this.year + "month :" + this.month + "dayOfMonth" + this.dayOfMonth);
            calendar.set(year, month, dayOfMonth);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String taskDate = format.format(calendar.getTime());
            tasksList.add(new Task(nameOfTask, taskDate));
            Log.i("taskList size :", String.valueOf(tasksList.size()));
            fillList();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//        Toast.makeText(this, String.valueOf(info.position), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(((TextView) v.findViewById(R.id.taskName)).getText()), Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, String.valueOf(info.position), Toast.LENGTH_SHORT).show();
        Log.i("position", String.valueOf(info.position));
        switch (item.getItemId()) {
            case R.id.change_status:
                openChangeStatusDialog();
                break;
            case R.id.edit_task:
                openEditTaskDialog();
                break;
            case R.id.delete_task:
                //TODO make normal remove method
                tasksList.remove(info.position);
                tasksListAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void openEditTaskDialog() {
        final EditText ETNameOfTask = new EditText(this);
//        new MaterialDialog.Builder(this)
//                .positiveText("Choose Date")
//                .title("Enter the name of a task")
//                .content("sfddsfdsfdsfkdshfk jsdjflsdjfldsjkfsd")
//                .show();
        ETNameOfTask.setText(tasksList.get(info.position).getName());
        new AlertDialog.Builder(this)
                .setMessage("Enter the name of a task")
                .setView(ETNameOfTask)
                .setPositiveButton("Choose Date", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameOfTaskText = String.valueOf(ETNameOfTask.getText());
                        nameOfTask = nameOfTaskText;
                        isTaskBeingEdited = true;
                        showDatePickerDialog();
                    }
                })
                .create()
                .show();
    }

    private void openChangeStatusDialog() {
        CharSequence[] statuses = {"Completed", "In process", "Uncompleted"};
        new AlertDialog.Builder(this)
                .setTitle("Choose a status")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setSingleChoiceItems(statuses, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tasksList.get(info.position).setStatus(Task.Status.COMPLETED);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                tasksList.get(info.position).setStatus(Task.Status.IN_PROCESS);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                            case 2:
                                tasksList.get(info.position).setStatus(Task.Status.UNCOMPLETED);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }
}

