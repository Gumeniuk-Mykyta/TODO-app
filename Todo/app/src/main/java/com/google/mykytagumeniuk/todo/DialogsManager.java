package com.google.mykytagumeniuk.todo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.mykytagumeniuk.todo.MainActivityListView.isTaskBeingEdited;
import static com.google.mykytagumeniuk.todo.MainActivityListView.nameOfTask;
import static com.google.mykytagumeniuk.todo.MainActivityListView.uncompletedTasksList;

//import static AddOrEditTaskActivity.dateOfTaskTV;

/**
 * Created by Nikita on 11/26/2016.
 */

public class DialogsManager {
    public static AddOrEditTaskActivity addOrEditTaskActivity = new AddOrEditTaskActivity();

    private static final String FRAG_TAG_DATE_PICKER = "CalendarDatePicker";

    public static void openEditTaskDialog(final Activity activity, final Context context, final int id) {
        final EditText ETNameOfTask = new EditText(context);
        ETNameOfTask.setText(uncompletedTasksList.get(id).getName());
        new MaterialDialog.Builder(context)
                .positiveText("Choose Date")
                .title("Enter the name of a task")
//                .customView(ETNameOfTask, true)
                .customView(R.layout.add_new_task, true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String nameOfTaskText = String.valueOf(ETNameOfTask.getText());
                        nameOfTask = nameOfTaskText;
                        isTaskBeingEdited = true;
                        showCalendarDialog((AppCompatActivity) activity);
//                        showDatePickerDialog();
                    }
                })
                .show();
    }

    public static void showCalendarDialog(Activity activity) {
        final Calendar calendar = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                        String date = format.format(calendar.getTime());
                        AddOrEditTaskActivity.dateOfTaskTV.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    public static void showTimePickerDialog(Activity activity) {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                    AddOrEditTaskActivity.timeOfTaskTV.setText(hourOfDay + ":" + minute);
            }
        },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true);

        tpd.show(activity.getFragmentManager(), "Timepickerdialog");

//        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = new CalendarDatePickerDialogFragment()
//                .setDoneText("Set Date")
//                .setCancelText("Cancel")
//                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
//                        addOrEditTaskActivity.test();
//                    }
//                })
//                .setOnDismissListener(new CalendarDatePickerDialogFragment.OnDialogDismissListener() {
//                    @Override
//                    public void onDialogDismiss(DialogInterface dialoginterface) {
//                        addOrEditTaskActivity.test();
//                    }
//                })
//                .setFirstDayOfWeek(Calendar.SUNDAY);
//        calendarDatePickerDialogFragment.show(activity.getSupportFragmentManager(), "c");
    }

    public void onDateEditTextClick(View view) {
        Log.i("click test", "test");
    }

//    public static void showLongTapDialog(final Activity activity, final Context context, final int id) {
//        CharSequence[] actions = {"Change status", "Edit task", "Delete task"};
//        new MaterialDialog.Builder(context)
//                .title("Chose an Action")
//                .items(actions)
//                .itemsCallback(new MaterialDialog.ListCallback() {
//                    @Override
//                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                        switch (which) {
//                            case 0:
//                                showChangeStatusDialog(activity, id);
//                                break;
//                            case 1:
//                                openEditTaskDialog(activity, context, id);
//                                break;
//                            case 2:
//                                uncompletedTasksList.remove(id);
//                                tasksListAdapter.notifyDataSetChanged();
//                                break;
//                        }
//                    }
//                })
//                .show();
//    }
}
