package com.example.nikita.todo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.nikita.todo.MainActivityListView.fillList;
import static com.example.nikita.todo.MainActivityListView.info;
import static com.example.nikita.todo.MainActivityListView.isTaskBeingEdited;
import static com.example.nikita.todo.MainActivityListView.nameOfTask;
import static com.example.nikita.todo.MainActivityListView.tasksList;
import static com.example.nikita.todo.MainActivityListView.tasksListAdapter;

/**
 * Created by Nikita on 11/29/2016.
 */

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
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


    private void showDatePickerDialog(Activity activity) {
        DialogFragment datePickerDialogFragment = new DatePickerDialog();
        datePickerDialogFragment.show(activity.getFragmentManager(), "DatePickerDialog");
    }
}
