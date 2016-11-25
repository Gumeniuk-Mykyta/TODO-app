package com.example.nikita.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Nikita on 10/4/2016.
 */
public class TasksListAdapter extends BaseAdapter {
    private ArrayList<Task> tasksList;
    private Context context;

    public TasksListAdapter(ArrayList<Task> tasksList, Context context) {
        this.tasksList = tasksList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View taskView = convertView;
        if (taskView == null) {
            taskView = LayoutInflater.from(context).inflate(R.layout.items_layout, null);
        }
        Task task = tasksList.get(position);
        if (task.getStatus() == Task.Status.COMPLETED) {
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.status_completed);
//            taskView.setBackgroundColor(taskView.getResources().ge
// tColor(R.color.completedTask));
        }
        if (task.getStatus() == Task.Status.IN_PROCESS) {
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.status_in_process);
//            taskView.setBackgroundColor(taskView.getResources().getColor(R.color.inProcessTask));

        }
        if (task.getStatus() == Task.Status.UNCOMPLETED) {
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.status_uncompleted);
//            taskView.setBackgroundColor(taskView.getResources().getColor(R.color.uncompletedTask));
        }
        TextView taskName = (TextView) taskView.findViewById(R.id.taskName);
        TextView taskDate = (TextView) taskView.findViewById(R.id.taskDate);
        taskName.setText(task.getName());
        taskDate.setText(task.getDate());
        return taskView;
    }

}
