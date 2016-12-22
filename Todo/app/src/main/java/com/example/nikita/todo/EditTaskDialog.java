package com.example.nikita.todo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import static com.example.nikita.todo.DialogsManager.showCalendarDialog;
import static com.example.nikita.todo.MainActivityListView.isTaskBeingEdited;
import static com.example.nikita.todo.MainActivityListView.nameOfTask;
import static com.example.nikita.todo.MainActivityListView.uncompletedTasksList;

/**
 * Created by Nikita on 11/30/2016.
 */

public class EditTaskDialog extends DialogFragment {
    int id;
    static EditTaskDialog newInstance(int id) {
        EditTaskDialog editTaskDialog = new EditTaskDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        editTaskDialog.setArguments(args);
        return editTaskDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        final EditText ETNameOfTask = new EditText(getActivity());
        ETNameOfTask.setText(uncompletedTasksList.get(id).getName());
        MaterialDialog editTaskDialog = new MaterialDialog.Builder(getActivity())
                .positiveText("Choose Date")
                .title("Enter the name of a task")
                .customView(ETNameOfTask, true)
                .customView(R.layout.add_new_task, true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String nameOfTaskText = String.valueOf(ETNameOfTask.getText());
                        nameOfTask = nameOfTaskText;
                        isTaskBeingEdited = true;
                        showCalendarDialog((AppCompatActivity) getActivity());
//                        showDatePickerDialog();
                    }
                })
                .show();

        return editTaskDialog;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View dialogView = inflater.inflate(R.layout.add_new_task, container, false);
//        EditText dateET = (EditText) dialogView.findViewById(R.id.dateET);
//        dateET.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"test onClick",Toast.LENGTH_SHORT).show();
//            }
//        });
//        return dialogView;
//    }

    public void onClick(View v){
        Toast.makeText(getActivity(),"test onCLick",Toast.LENGTH_SHORT).show();
    }


    public static void showEditTaskDialog(Activity activity, int id){
        DialogFragment editTaskDialogFragent = EditTaskDialog.newInstance(id);
        editTaskDialogFragent.show(activity.getFragmentManager(),"editTaskDialogFragment");
    }

    public void openAddOrEditTaskActivity(Context context, int id, ArrayList<Task> tasksList) {
//        if (isAdded()){
        Intent openAddOrEditTaskActivity = new Intent(context, AddOrEditTaskActivity.class);
//        this.dismiss();
       context.startActivity(openAddOrEditTaskActivity);
    }

}
