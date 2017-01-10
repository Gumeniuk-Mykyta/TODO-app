package com.vymirs.mykytagumeniuk.dayplanner;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Nikita on 11/26/2016.
 */

public class DialogsManager {

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
                String hour = String.valueOf(hourOfDay);
                String min = String.valueOf(minute);
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                }
                if (minute < 10) {
                    min = "0" + minute;
                }
                AddOrEditTaskActivity.timeOfTaskTV.setText(hour + ":" + min);
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

        tpd.show(activity.getFragmentManager(), "Timepickerdialog");

    }

    public void onDateEditTextClick(View view) {
        Log.i("click isNoDate", "isNoDate");
    }

}
