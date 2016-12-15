package com.example.nikita.todo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;

public class AddOrEditTaskActivity extends AppCompatActivity {
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_task);
        EditText dateET = (EditText) findViewById(R.id.dateET);
        activity = this;
        dateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogsManager.showCalendarDialog((AppCompatActivity) activity);
                }
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dateET:
                DialogsManager.showCalendarDialog(this);
                break;
            case R.id.button:
                DialogsManager.showCalendarDialog(this);
                break;
//                 CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = new CalendarDatePickerDialogFragment()
//                .setDoneText("Set Date")
//                    .setCancelText("Cancel")
//                    .setFirstDayOfWeek(Calendar.SUNDAY);
//                calendarDatePickerDialogFragment.show(getSupportFragmentManager(), "c");
        }
    }
}
