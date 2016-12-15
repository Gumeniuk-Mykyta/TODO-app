package com.example.nikita.todo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Nikita on 11/26/2016.
 */

public class ActionBar {
    public static void initActionBar(final AppCompatActivity activity) {
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab()
                .setText("All tasks")
                .setTabListener(new android.support.v7.app.ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                        Toast.makeText(activity.getApplicationContext(),"all tasks tab was selected",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

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
                        Toast.makeText(activity.getApplicationContext(),"today's tasks tab was selected",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

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

                    }

                    @Override
                    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

                    }

                    @Override
                    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                }));

    }
}
