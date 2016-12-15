package com.example.nikita.todo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import static com.example.nikita.todo.ChangeStatusDialog.showChangeStatusDialog;
import static com.example.nikita.todo.DialogsManager.openEditTaskDialog;
//import static com.example.nikita.todo.EditTaskDialog.openAddOrEditTaskActivity;
import static com.example.nikita.todo.MainActivityListView.tasksList;
import static com.example.nikita.todo.MainActivityListView.tasksListAdapter;
import static com.example.nikita.todo.EditTaskDialog.showEditTaskDialog;

/**
 * Created by Nikita on 11/29/2016.
 */

public class LongTapDialog extends DialogFragment {
    int id;
    Activity activity;

    static LongTapDialog newInstance(int id) {
        LongTapDialog longTapDialog = new LongTapDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        longTapDialog.setArguments(args);
        return longTapDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        final CharSequence[] actions = {"Change status", "Edit task", "Delete task"};
        MaterialDialog longTapDialog = new MaterialDialog.Builder(getActivity())
                .title("Chose an Action")
                .items(actions)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                showChangeStatusDialog(getActivity(), id);
                                break;
                            case 1:
                                EditTaskDialog editTaskDialog = new EditTaskDialog();
                                editTaskDialog.openAddOrEditTaskActivity(getActivity());
//                                openAddOrEditTaskActivity(getActivity());
//                                showEditTaskDialog(getActivity(), id);
                                break;
                            case 2:
                                tasksList.remove(id);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                })
                .show();
        return longTapDialog;
    }

    public void showLongTapDialog(Activity activity, int id) {
        DialogFragment longTapDialogFragment = LongTapDialog.newInstance(id);
        longTapDialogFragment.show(activity.getFragmentManager(), "LongTapDialogFragment");

    }
}
