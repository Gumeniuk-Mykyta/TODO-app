package com.example.nikita.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.zip.Inflater;

public class MainActivityListView extends Activity {
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

