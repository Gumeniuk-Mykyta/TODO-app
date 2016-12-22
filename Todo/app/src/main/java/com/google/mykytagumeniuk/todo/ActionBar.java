package com.google.mykytagumeniuk.todo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Nikita on 11/26/2016.
 */

public class ActionBar {
    //    public static Context context;
//    public ActionBar(Context context){
//        this.context = context;
//    }
    static boolean isAllTasksTabSelected = false;
    static boolean isCompletedTabSelected = false;
    static boolean isTodaysTabSelected = false;

    public static void initActionBar(final AppCompatActivity activity) {
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab()
                .setText("All tasks")
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isAllTasksTabSelected = true;
                        Toast.makeText(activity.getApplicationContext(), "all tasks tab was selected", Toast.LENGTH_SHORT).show();
                        if (MainActivityListView.tasksListAdapter != null) {
//                        if (wasAllTasksTabUnselected) {
                            MainActivityListView.tasksListAdapter = new TasksListAdapter(MainActivityListView.uncompletedTasksList, activity.getApplicationContext());
                            MainActivityListView.fillList();
                        }
                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isAllTasksTabSelected = false;
                    }

                    @Override
                    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                }));
        actionBar.addTab(actionBar.newTab()
                .setText("Today's tasks")
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        Toast.makeText(activity.getApplicationContext(), "today's tasks tab was selected", Toast.LENGTH_SHORT).show();
                        isTodaysTabSelected = true;
                            MainActivityListView.tasksListAdapter = new TasksListAdapter(MainActivityListView.todayTasksList, activity.getApplicationContext());
                            MainActivityListView.fillList();
                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isTodaysTabSelected = false;
                    }

                    @Override
                    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                }));
        actionBar.addTab(actionBar.newTab()
                .setText("Completed")
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isCompletedTabSelected = true;
                        MainActivityListView.tasksListAdapter = new TasksListAdapter(MainActivityListView.completedTasksList, activity.getApplicationContext());
                        MainActivityListView.fillList();
                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isCompletedTabSelected = false;
                    }

                    @Override
                    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                }));

    }
}
