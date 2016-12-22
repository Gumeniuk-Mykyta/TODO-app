package com.example.nikita.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import static com.example.nikita.todo.AddOrEditTaskActivity.isAddTaskSelected;
import static com.example.nikita.todo.MainActivityListView.completedTasksList;
import static com.example.nikita.todo.MainActivityListView.todayTasksList;
import static com.example.nikita.todo.MainActivityListView.uncompletedTasksList;

/**
 * Created by Nikita on 11/26/2016.
 */

public class FABMenuManager {
    static TextView TVSortBy;
    static TextView TVAddANewTask;
    static FloatingActionButton fabMain;
    static FloatingActionButton fabSortBy;
    static FloatingActionButton fabAddANewTask;
//    static Animation fabOpen;
//    static Animation fabClose;
//    static Animation rotateClockWise;
//    static Animation rotateAnticlockwise;
//    static boolean isFabMenuOpen = false;

    public static void initFabMenu(Activity activity) {

        fabMain = (FloatingActionButton) activity.findViewById(R.id.fab_main);
        fabSortBy = (FloatingActionButton) activity.findViewById(R.id.fab_sort_by);
        fabAddANewTask = (FloatingActionButton) activity.findViewById(R.id.fab_add_a_new_task);
        TVSortBy = (TextView) activity.findViewById(R.id.tv_sort_by);
        TVAddANewTask = (TextView) activity.findViewById(R.id.tv_add_a_new_task);
        fabMain.setClickable(false);
        fabSortBy.setClickable(false);
        TVSortBy.setClickable(false);
        TVAddANewTask.setClickable(false);
        fabMain.setVisibility(View.GONE);
        fabSortBy.setVisibility(View.GONE);
        TVSortBy.setVisibility(View.GONE);
        TVAddANewTask.setVisibility(View.GONE);
//        fabOpen = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.fab_open);
//        fabClose = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.fab_close);
//        rotateAnticlockwise = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.rotate_anticlockwise);
//        rotateClockWise = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.rotate_clockwise);
    }

    public static void onFABButtonClick(View view, Context context) {
        switch (view.getId()) {
//            case R.id.fab_main:
//                if (isFabMenuOpen) {
//                    ViewCompat.animate(fabMain).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
//                    fabSortBy.startAnimation(fabClose);
//                    fabAddANewTask.startAnimation(fabClose);
//                    TVAddANewTask.startAnimation(fabClose);
//                    TVSortBy.startAnimation(fabClose);
//                    fabSortBy.setClickable(false);
//                    fabAddANewTask.setClickable(false);
//                    TVAddANewTask.setClickable(false);
//                    TVSortBy.setClickable(false);
//                    isFabMenuOpen = false;
//                } else {
//                    ViewCompat.animate(fabMain).rotation(90.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
//                    fabSortBy.startAnimation(fabOpen);
//                    fabAddANewTask.startAnimation(fabOpen);
//                    TVAddANewTask.startAnimation(fabOpen);
//                    TVSortBy.startAnimation(fabOpen);
//                    fabSortBy.setClickable(true);
//                    fabAddANewTask.setClickable(true);
//                    TVAddANewTask.setClickable(true);
//                    TVSortBy.setClickable(true);
//                    isFabMenuOpen = true;
//                }
//                break;
//            case R.id.tv_add_a_new_task:
            case R.id.fab_add_a_new_task:
                isAddTaskSelected = true;
                if (ActionBar.isAllTasksTabSelected){
                    AddOrEditTaskActivity.tasksList = uncompletedTasksList;
                }
                if (ActionBar.isTodaysTabSelected){
                    AddOrEditTaskActivity.tasksList = todayTasksList;
                }
                if (ActionBar.isCompletedTabSelected){
                    AddOrEditTaskActivity.tasksList = completedTasksList;
                }
                Intent openAddOrEditTaskActivity = new Intent(context,AddOrEditTaskActivity.class);
                context.startActivity(openAddOrEditTaskActivity);
                break;
//            case R.id.tv_sort_by:
//            case R.id.fab_sort_by:
//                Toast.makeText(context, "sort by", Toast.LENGTH_SHORT).show();
//                break;
        }
    }
}