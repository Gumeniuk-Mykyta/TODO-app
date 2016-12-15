package com.example.nikita.todo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import static com.example.nikita.todo.MainActivityListView.tasksList;
import static com.example.nikita.todo.MainActivityListView.tasksListAdapter;

/**
 * Created by Nikita on 11/29/2016.
 */

public class ChangeStatusDialog extends DialogFragment {
    int id;
    int chosenStatus = -1;

    static ChangeStatusDialog newInstance(int id) {
        ChangeStatusDialog ChangeStatusDialog = new ChangeStatusDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        ChangeStatusDialog.setArguments(args);
        return ChangeStatusDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        id = getArguments().getInt("id");
        switch (tasksList.get(id).getStatus()) {
            case COMPLETED:
                chosenStatus = 0;
                break;
            case IN_PROCESS:
                chosenStatus = 1;
                break;
            case UNCOMPLETED:
                chosenStatus = 2;
                break;
        }
        CharSequence[] statuses = {"Completed", "In process", "Uncompleted"};
        MaterialDialog changeStatusDialog = new MaterialDialog.Builder(getActivity())
                .title("Choose a status")
                .positiveText("Ok")
                .items(statuses)
                .itemsCallbackSingleChoice(chosenStatus, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                tasksList.get(id).setStatus(Task.Status.COMPLETED);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                tasksList.get(id).setStatus(Task.Status.IN_PROCESS);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                            case 2:
                                tasksList.get(id).setStatus(Task.Status.UNCOMPLETED);
                                tasksListAdapter.notifyDataSetChanged();
                                break;
                        }
                        return true;
                    }
                })
                .show();
        return changeStatusDialog;
    }

    public static void showChangeStatusDialog(Activity activity, final int id) {
        DialogFragment changeStatusDialogFragment = ChangeStatusDialog.newInstance(id);
        changeStatusDialogFragment.show(activity.getFragmentManager(), "ChangeStatusDialogFragment");
    }

}
