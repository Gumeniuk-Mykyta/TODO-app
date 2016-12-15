package com.example.nikita.todo;

import android.app.*;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;

import static com.example.nikita.todo.ChangeStatusDialog.showChangeStatusDialog;
import static com.example.nikita.todo.MainActivityListView.isTaskBeingEdited;
import static com.example.nikita.todo.MainActivityListView.nameOfTask;
import static com.example.nikita.todo.MainActivityListView.tasksList;
import static com.example.nikita.todo.MainActivityListView.tasksListAdapter;

/**
 * Created by Nikita on 11/26/2016.
 */

public class DialogsManager {

    private static final java.lang.String FRAG_TAG_DATE_PICKER = "CalendarDatePicker";

    public static void openEditTaskDialog(final Activity activity, final Context context, final int id) {
        final EditText ETNameOfTask = new EditText(context);
        ETNameOfTask.setText(tasksList.get(id).getName());
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

    public static void showCalendarDialog(AppCompatActivity activity) {
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = new CalendarDatePickerDialogFragment()
                .setDoneText("Set Date")
                .setCancelText("Cancel")
                .setFirstDayOfWeek(Calendar.SUNDAY);
        calendarDatePickerDialogFragment.show(activity.getSupportFragmentManager(), "c");
    }

    public void onDateEditTextClick(View view){
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
//                                tasksList.remove(id);
//                                tasksListAdapter.notifyDataSetChanged();
//                                break;
//                        }
//                    }
//                })
//                .show();
//    }
}
