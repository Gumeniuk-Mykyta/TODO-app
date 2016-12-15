package com.example.nikita.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Nikita on 12/15/2016.
 */

public class DBQueryManager {
    SQLiteDatabase db;

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
                    task.setStatus(Task.Status.IN_PROCESS);
                    break;
            }
            tasksList.add(task);
        }
    }

    public void sort() {

    }

    public void insertTaskToDB(ArrayList<Task> tasksList) {
        ContentValues contentValues = new ContentValues();
        for (Task task : tasksList) {
            contentValues.put(DBHelper.NAME_OF_TASK, task.getName());
            contentValues.put(DBHelper.DATE_OF_TASK, task.getDate());
            switch (task.getStatus()) {
                case COMPLETED:
                    contentValues.put(DBHelper.STATUS_OF_TASK, "Completed");
                    break;
                case UNCOMPLETED:
                    contentValues.put(DBHelper.STATUS_OF_TASK, "Uncompleted");
                    break;
                case IN_PROCESS:
                    contentValues.put(DBHelper.STATUS_OF_TASK, "In_process");
                    break;
            }
            db.insert(DBHelper.TASKS_TABLE, null, contentValues);
        }
    }

    public void readTasksFromDB() {
//        Cursor cursor = db.query(db.TASKS_TABLE,null,null,null,null);
    }

    public void deleteTaskFromDB() {
    }
}
