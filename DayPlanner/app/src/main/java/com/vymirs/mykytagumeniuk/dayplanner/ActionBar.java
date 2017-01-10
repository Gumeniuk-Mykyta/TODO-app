package com.vymirs.mykytagumeniuk.dayplanner;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nikita on 11/26/2016.
 */

public class ActionBar {
    static boolean isAllTasksTabSelected = false;
    static boolean isCompletedTabSelected = false;
    static boolean isTodaysTabSelected = false;

    public static void initActionBar(final AppCompatActivity activity) {
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab()
                .setText(R.string.all_tasks)
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        isAllTasksTabSelected = true;
                        if (MainActivityListView.tasksListAdapter != null) {
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
                .setText(R.string.todays_tasks)
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
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
                .setText(R.string.completed_tasks)
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
