package com.vymirs.mykytagumeniuk.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Nikita on 12/15/2016.
 */

public class DBQueryManager {
    SQLiteDatabase db;
    String orderBy;

    public DBQueryManager(DBHelper db, Context context) {
        db = new DBHelper(context);
        this.db = db.getWritableDatabase();
    }

    public void readCompletedTasksFromDB(ArrayList<Task> tasksList) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TASKS_TABLE + " WHERE " +
                DBHelper.STATUS_OF_TASK + " = 'Completed';", null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(DBHelper.NAME_OF_TASK);
            int dateIndex = cursor.getColumnIndex(DBHelper.DATE_OF_TASK);
            int statusIndex = cursor.getColumnIndex(DBHelper.STATUS_OF_TASK);
            Task task = new Task();
            task.setName(cursor.getString(nameIndex));
            task.setDate(cursor.getString(dateIndex));
            switch (cursor.getString(statusIndex)) {
                case "Uncompleted":
                    task.setStatus(Task.Status.UNCOMPLETED);
                    break;
                case "Completed":
                    task.setStatus(Task.Status.COMPLETED);
                    break;
                case "In_process":
                    task.setStatus(Task.Status.IN_PROGRESS);
                    break;
            }
            tasksList.add(task);
        }
    }

    public void sort() {

    }

    public void insertTasksToDB() {
        clearDB();
        insertTasksToDBFromArray(MainActivityListView.uncompletedTasksList);
        insertTasksToDBFromArray(MainActivityListView.completedTasksList);

    }

    private void clearDB() {
        db.delete(DBHelper.TASKS_TABLE, null, null);
        db.execSQL("delete from sqlite_sequence where name='" + DBHelper.TASKS_TABLE + "'");
    }

    public void readTasksFromDB() {
        orderBy = "case when " + DBHelper.STATUS_OF_TASK + " like " + "'In_progress'" + " then 0 " +
                "when " + DBHelper.STATUS_OF_TASK + " like " + "'Uncompleted'" + " then 1 " +
                "else 2 end, " + "case when " + DBHelper.DATE_OF_TASK + " like '' then 1 else 0 end" +
                 ", " + "case when " + DBHelper.TIME_OF_TASK + " like '' then 1 else 0 end, " + DBHelper.NAME_OF_TASK + ";";
        MainActivityListView.completedTasksList.clear();
        MainActivityListView.uncompletedTasksList.clear();
        MainActivityListView.todayTasksList.clear();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String date = format.format(calendar.getTime());
        Cursor cursor = db.query(DBHelper.TASKS_TABLE, null, null, null, null, null, orderBy);
        while (cursor.moveToNext()) {
            int nameOfTaskIndex = cursor.getColumnIndex(DBHelper.NAME_OF_TASK);
            int dateOfTaskIndex = cursor.getColumnIndex(DBHelper.DATE_OF_TASK);
            int timeOfTaskIndex = cursor.getColumnIndex(DBHelper.TIME_OF_TASK);
            int desciptionOfTaskIndex = cursor.getColumnIndex(DBHelper.DESCRIPTION_OF_TASK);
            int statusOfTaskIndex = cursor.getColumnIndex(DBHelper.STATUS_OF_TASK);
            String nameOfTask = (cursor.getString(nameOfTaskIndex));
            String dateOfTask = (cursor.getString(dateOfTaskIndex));
            String timeOfTask = (cursor.getString(timeOfTaskIndex));
            String descriptionOfTask = (cursor.getString(desciptionOfTaskIndex));
            String statusOfTask = (cursor.getString(statusOfTaskIndex));
            Task task = new Task();
            task.setName(nameOfTask);
            task.setDate(dateOfTask);
            task.setTime(timeOfTask);
            task.setDescription(descriptionOfTask);
            if (statusOfTask.equals(DBHelper.STATUS_COMPLETED)) {
                task.setStatus(Task.Status.COMPLETED);
                MainActivityListView.completedTasksList.add(task);
            }
            if (statusOfTask.equals(DBHelper.STATUS_UNCOMPLETED)) {
                task.setStatus(Task.Status.UNCOMPLETED);
                MainActivityListView.uncompletedTasksList.add(task);
            }
            if (statusOfTask.equals(DBHelper.STATUS_IN_PROGRESS)) {
                task.setStatus(Task.Status.IN_PROGRESS);
                MainActivityListView.uncompletedTasksList.add(task);
            }
            if (dateOfTask.equals(date)) {
                MainActivityListView.todayTasksList.add(task);
            }
        }
    }

    public void deleteTaskFromDB() {
    }

    private void insertTasksToDBFromArray(ArrayList<Task> tasksList) {
        ContentValues contentValues = new ContentValues();
        for (Task task : tasksList) {
            contentValues.put(DBHelper.NAME_OF_TASK, task.getName());
            contentValues.put(DBHelper.DATE_OF_TASK, task.getDate());
            contentValues.put(DBHelper.TIME_OF_TASK, task.getTime());
            contentValues.put(DBHelper.DESCRIPTION_OF_TASK, task.getDescription());
            switch (task.getStatus()) {
                case COMPLETED:
                    contentValues.put(DBHelper.STATUS_OF_TASK, DBHelper.STATUS_COMPLETED);
                    break;
                case UNCOMPLETED:
                    contentValues.put(DBHelper.STATUS_OF_TASK, DBHelper.STATUS_UNCOMPLETED);
                    break;
                case IN_PROGRESS:
                    contentValues.put(DBHelper.STATUS_OF_TASK, DBHelper.STATUS_IN_PROGRESS);
                    break;
            }
            db.insert(DBHelper.TASKS_TABLE, null, contentValues);
        }
    }
}
