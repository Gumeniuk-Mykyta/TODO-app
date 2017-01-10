package com.vymirs.mykytagumeniuk.dayplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import static com.vymirs.mykytagumeniuk.dayplanner.AddOrEditTaskActivity.isAddTaskSelected;
import static com.vymirs.mykytagumeniuk.dayplanner.MainActivityListView.completedTasksList;
import static com.vymirs.mykytagumeniuk.dayplanner.MainActivityListView.todayTasksList;
import static com.vymirs.mykytagumeniuk.dayplanner.MainActivityListView.uncompletedTasksList;

/**
 * Created by Nikita on 11/26/2016.
 */

public class FABMenuManager {

    public static void initFabMenu(Activity activity) {

    }

    public static void onFABButtonClick(View view, Context context) {
        switch (view.getId()) {
//
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
//
        }
    }
}