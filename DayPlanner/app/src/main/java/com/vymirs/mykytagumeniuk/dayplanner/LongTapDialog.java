package com.vymirs.mykytagumeniuk.dayplanner;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import static com.vymirs.mykytagumeniuk.dayplanner.AddOrEditTaskActivity.idOfTask;
import static com.vymirs.mykytagumeniuk.dayplanner.AddOrEditTaskActivity.isAddTaskSelected;
import static com.vymirs.mykytagumeniuk.dayplanner.ChangeStatusDialog.showChangeStatusDialog;


/**
 * Created by Nikita on 11/29/2016.
 */

public class LongTapDialog extends DialogFragment {
    int id;
    Activity activity;
    ArrayList<Task> tasksList;

    static LongTapDialog newInstance(int id, ArrayList<Task> tasksList) {
        LongTapDialog longTapDialog = new LongTapDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putParcelableArrayList("tasksList",tasksList);
        longTapDialog.setArguments(args);
        return longTapDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        tasksList = getArguments().getParcelableArrayList("tasksList");
        final CharSequence[] actions = {getString(R.string.change_status), getString(R.string.edit_task), getString(R.string.delete_task)};
        MaterialDialog longTapDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.choose_an_action)
                .items(actions)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                showChangeStatusDialog(getActivity(), id, tasksList);
                                break;
                            case 1:
                                isAddTaskSelected = false;
                                idOfTask = id;
                                AddOrEditTaskActivity.tasksList = tasksList;
                                Intent openAddOrEditTaskActivity = new Intent(getActivity(),AddOrEditTaskActivity.class);
                                getActivity().startActivity(openAddOrEditTaskActivity);
                                break;
                            case 2:
                                tasksList.remove(id);
                                MainActivityListView.tasksListAdapter.notifyDataSetChanged();
                                MainActivityListView.dbQueryManager.insertTasksToDB();
                                MainActivityListView.dbQueryManager.readTasksFromDB();
                                break;
                        }
                    }
                })
                .show();
        return longTapDialog;
    }

    public void showLongTapDialog(Activity activity, int id, ArrayList<Task> tasksList) {
        DialogFragment longTapDialogFragment = LongTapDialog.newInstance(id, tasksList);
        longTapDialogFragment.show(activity.getFragmentManager(), "LongTapDialogFragment");

    }
}
