package com.vymirs.mykytagumeniuk.dayplanner;

import android.app.Activity;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddOrEditTaskActivity extends AppCompatActivity {
    public static boolean isAddTaskSelected;
    Activity activity;
    public static TextView timeOfTaskTV;
    public static TextView dateOfTaskTV;
    public static EditText descriptionOfTaskET;
    public static EditText nameOfTaskET;
    public static View lineBelowTimeOfTask;
    public static View lineBelowDateOfTask;
    public static View lineBelowNameOfTask;
    public static View lineBelowDescriptionOfTask;
    public static int idOfTask;
    public static ScrollView scrollView;
    public static ArrayList<Task> tasksList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_task);
        activity = this;
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        lineBelowNameOfTask = findViewById(R.id.lineBelowNameOfTask);
        lineBelowDescriptionOfTask = findViewById(R.id.lineBelowDescriptionOfTask);
        nameOfTaskET = (EditText) findViewById(R.id.nameOfTaskET);
        dateOfTaskTV = (TextView) findViewById(R.id.dateOfTaskTV);
        timeOfTaskTV = (TextView) findViewById(R.id.timeOfTaskTV);
        descriptionOfTaskET = (EditText) findViewById(R.id.descriptionOfTaskET);

        Button button = (Button) findViewById(R.id.addOrEditTaskButton);
        if (isAddTaskSelected) {
            dateOfTaskTV.setText("");
            timeOfTaskTV.setText("");
            button.setText(R.string.add_task);
            if (ActionBar.isTodaysTabSelected){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                String date = format.format(calendar.getTime());
                dateOfTaskTV.setText(date);
            }
        }
        if (!isAddTaskSelected) {
            button.setText(R.string.save_changes);
            nameOfTaskET.setText(tasksList.get(idOfTask).getName());
            dateOfTaskTV.setText(tasksList.get(idOfTask).getDate());
            timeOfTaskTV.setText(tasksList.get(idOfTask).getTime());
            descriptionOfTaskET.setText(tasksList.get(idOfTask).getDescription());
        }
    }


    public void onClick(View view) {
        View lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        View lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        switch (view.getId()) {
            case R.id.dateOfTaskTV:
                DialogsManager.showCalendarDialog(this);
                break;
            case R.id.lineBelowDateOfTask:
                DialogsManager.showCalendarDialog(this);
                break;
            case R.id.addOrEditTaskButton:
                if (isAddTaskSelected) {
                        addTask();


                } else if (!isAddTaskSelected) {

                    tasksList.get(idOfTask).setName(nameOfTaskET.getText().toString());
                    tasksList.get(idOfTask).setDate(dateOfTaskTV.getText().toString());
                    tasksList.get(idOfTask).setTime(timeOfTaskTV.getText().toString());
                    tasksList.get(idOfTask).setDescription(descriptionOfTaskET.getText().toString());
                    MainActivityListView.tasksListAdapter.notifyDataSetChanged();
                    finish();
                    if (DescriptionOfTask.isDescriptionOfTaskOpened) {
                        Intent descriptionOfTaskIntent = new Intent(this, DescriptionOfTask.class);
                        descriptionOfTaskIntent.putExtra("id", idOfTask);
                        DescriptionOfTask.tasksList = tasksList;
                        this.startActivity(descriptionOfTaskIntent);
                    }
                }
                MainActivityListView.dbQueryManager.insertTasksToDB();
                MainActivityListView.dbQueryManager.readTasksFromDB();
                break;
            case R.id.timeOfTaskTV:
                DialogsManager.showTimePickerDialog(this);
                break;
            case R.id.lineBelowTimeOfTask:
                DialogsManager.showTimePickerDialog(this);
                break;
            case R.id.nameOfTaskET:
                break;
            case R.id.lineBelowNameOfTask:
                break;
            case R.id.descriptionOfTaskET:
                break;
            case R.id.lineBelowDescriptionOfTask:
                break;
        }
    }

    private void addTask() {
        Task task = new Task();
        task.setName(nameOfTaskET.getText().toString());
        task.setDate(dateOfTaskTV.getText().toString());
        task.setTime(timeOfTaskTV.getText().toString());
        task.setDescription(descriptionOfTaskET.getText().toString());
        if (tasksList != MainActivityListView.completedTasksList) {
            task.setStatus(Task.Status.UNCOMPLETED);
            MainActivityListView.uncompletedTasksList.add(task);
        } else {
            task.setStatus(Task.Status.COMPLETED);
            MainActivityListView.completedTasksList.add(task);
        }
//        tasksList.add(task);
        MainActivityListView.tasksListAdapter.notifyDataSetChanged();
        finish();
    }
}
