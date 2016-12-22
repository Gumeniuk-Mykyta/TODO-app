package com.example.nikita.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.new_completed);
//            taskView.setBackgroundColor(taskView.getResources().ge
// tColor(R.color.completedTask));
        }
        if (task.getStatus() == Task.Status.IN_PROGRESS) {
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.new_in_process);
//            taskView.setBackgroundColor(taskView.getResources().getColor(R.color.inProcessTask));

        }
        if (task.getStatus() == Task.Status.UNCOMPLETED) {
            taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.new_uncompleted);
//            taskView.setBackgroundColor(taskView.getResources().getColor(R.color.uncompletedTask));
        }
        TextView taskName = (TextView) taskView.findViewById(R.id.taskName);
        TextView taskDate = (TextView) taskView.findViewById(R.id.taskDate);
        taskName.setText(task.getName());
        if (task.getDate() != null && !task.getDate().equals("")){
            String day = task.getDate().substring(task.getDate().length() - 2);
            String month = task.getDate().substring(task.getDate().length() - 5,task.getDate().length() - 3);
        taskDate.setText(day + "." + month);
        }
        if (task.getDate().equals("")){
            taskDate.setText("");
        }
        if (task.getTime() != null && !task.getTime().equals("")){
            taskDate.append(" / " + task.getTime());
        }
        return taskView;
    }
//        if (tasksList.get(position).getStatus()== Task.Status.UNCOMPLETED)
//        if (ActionBar.isAllTasksTabSelected) {
//            if (tasksList.get(position).getStatus() == Task.Status.UNCOMPLETED ||
//                    tasksList.get(position).getStatus() == Task.Status.IN_PROGRESS) {
//                View taskView = convertView;
//                if (taskView == null) {
//                    taskView = LayoutInflater.from(context).inflate(R.layout.items_layout, null);
//                }
//                Task task = tasksList.get(position);
//
//                if (task.getStatus() == Task.Status.IN_PROGRESS) {
//                    taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.ic_minus);
//
//                }
//                if (task.getStatus() == Task.Status.UNCOMPLETED) {
//                    taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.ic_error);
//                }
//                TextView taskName = (TextView) taskView.findViewById(R.id.taskName);
//                TextView taskDate = (TextView) taskView.findViewById(R.id.taskDate);
//                taskName.setText(task.getName());
//                taskDate.setText(task.getDate());
//                return taskView;
//            }
//        }
//        if (ActionBar.isCompletedTabSelected) {
//            if (tasksList.get(position).getStatus() == Task.Status.COMPLETED) {
//                View taskView = convertView;
//                if (taskView == null) {
//                    taskView = LayoutInflater.from(context).inflate(R.layout.items_layout, null);
//                }
//                Task task = tasksList.get(position);
//                taskView.findViewById(R.id.item_status_icon).setBackgroundResource(R.mipmap.ic_sucess2);
//                TextView taskName = (TextView) taskView.findViewById(R.id.taskName);
//                TextView taskDate = (TextView) taskView.findViewById(R.id.taskDate);
//                taskName.setText(task.getName());
//                taskDate.setText(task.getDate());
//                return taskView;
//            }
//        }
//        View taskView = convertView;
//        if (taskView == null) {
//            taskView = LayoutInflater.from(context).inflate(R.layout.items_layout, null);
////            convertView.setEm
//        }
//        return taskView;
//    }
}

