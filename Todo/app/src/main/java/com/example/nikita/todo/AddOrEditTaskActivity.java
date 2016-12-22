package com.example.nikita.todo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import static com.example.nikita.todo.MainActivityListView.completedTasksList;
import static com.example.nikita.todo.MainActivityListView.tasksListAdapter;

public class AddOrEditTaskActivity extends AppCompatActivity {
    public static boolean isAddTaskSelected;
    Activity activity;
    public static TextView timeOfTaskTV;
    public static EditText nameOfTask;
    public static TextView dateOfTaskTV;
    public static View lineBelowTimeOfTask;
    public static View lineBelowDateOfTask;
    public static int idOfTask;
    public static ArrayList<Task> tasksList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_task);
        activity = this;
        lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        lineBelowDateOfTask.setBackgroundColor(getResources().getColor((R.color.black)));
        lineBelowTimeOfTask.setBackgroundColor(getResources().getColor((R.color.black)));
        nameOfTask = (EditText) findViewById(R.id.nameOfTaskET);
        dateOfTaskTV = (TextView) findViewById(R.id.dateOfTaskTV);
        timeOfTaskTV = (TextView) findViewById(R.id.timeOfTaskTV);
        Button button = (Button) findViewById(R.id.addOrEditTaskButton);
        if (isAddTaskSelected) {
            dateOfTaskTV.setText("");
            timeOfTaskTV.setText("");
            button.setText("Add Task");
        }
        if (!isAddTaskSelected) {
            button.setText("Save changes");
            nameOfTask.setText(tasksList.get(idOfTask).getName());
            dateOfTaskTV.setText(tasksList.get(idOfTask).getDate());
            timeOfTaskTV.setText(tasksList.get(idOfTask).getTime());
        }
    }


    public void onClick(View view) {
        View lineBelowDateOfTask = findViewById(R.id.lineBelowDateOfTask);
        View lineBelowTimeOfTask = findViewById(R.id.lineBelowTimeOfTask);
        switch (view.getId()) {
            case R.id.dateOfTaskTV:
                lineBelowDateOfTask.setBackgroundColor(getResources().getColor((R.color.colorAccent)));
                DialogsManager.showCalendarDialog(this);
//                lineBelowDateOfTask.setBackgroundColor(getResources().getColor((R.color.black)));
                break;
            case R.id.lineBelowDateOfTask:
                lineBelowDateOfTask.setBackgroundColor(getResources().getColor((R.color.colorAccent)));
                DialogsManager.showCalendarDialog(this);
//                lineBelowDateOfTask.setBackgroundColor(getResources().getColor((R.color.black)));
                break;
            case R.id.addOrEditTaskButton:
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                String date = calendar.get(java.util.Calendar.YEAR) + "." + (calendar.get(java.util.Calendar.MONTH) + 1) + "." + calendar.get(java.util.Calendar.DAY_OF_MONTH);
                if (isAddTaskSelected) {
                    if (ActionBar.isTodaysTabSelected) {
                        if (!dateOfTaskTV.getText().equals(date)) {
                            new MaterialDialog.Builder(this)
                                    .title("Wrong date")
                                    .content("You can't add task to today's task list, if this task has different date from today!\n Today is : " + date)
                                    .positiveText("OK")
                                    .show();
                        } else if (dateOfTaskTV.getText().equals(date)) {
                            addTask();
                        }
                    } else
                        addTask();


                } else if (!isAddTaskSelected) {

                    tasksList.get(idOfTask).setName(nameOfTask.getText().toString());
                    tasksList.get(idOfTask).setDate(dateOfTaskTV.getText().toString());
                    tasksList.get(idOfTask).setTime(timeOfTaskTV.getText().toString());
                    tasksListAdapter.notifyDataSetChanged();
                    finish();
                }
                MainActivityListView.dbQueryManager.insertTasksToDB();
                MainActivityListView.dbQueryManager.readTasksFromDB();
                break;
            case R.id.timeOfTaskTV:
                lineBelowTimeOfTask.setBackgroundColor(getResources().getColor((R.color.colorAccent)));
                DialogsManager.showTimePickerDialog(this);
                break;
            case R.id.lineBelowTimeOfTask:
                lineBelowTimeOfTask.setBackgroundColor(getResources().getColor((R.color.colorAccent)));
                DialogsManager.showTimePickerDialog(this);
                break;
//                 CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = new CalendarDatePickerDialogFragment()
//                .setDoneText("Set Date")
//                    .setCancelText("Cancel")
//                    .setFirstDayOfWeek(Calendar.SUNDAY);
//                calendarDatePickerDialogFragment.show(getSupportFragmentManager(), "c");
        }
    }

    private void addTask() {
        Task task = new Task();
        task.setName(nameOfTask.getText().toString());
        task.setDate(dateOfTaskTV.getText().toString());
        task.setTime(timeOfTaskTV.getText().toString());
        if (tasksList != completedTasksList) {
            task.setStatus(Task.Status.UNCOMPLETED);
        } else {
            task.setStatus(Task.Status.COMPLETED);
        }
        tasksList.add(task);
        tasksListAdapter.notifyDataSetChanged();
        finish();
    }
}
