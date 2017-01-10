package com.vymirs.mykytagumeniuk.dayplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v13.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.vymirs.mykytagumeniuk.dayplanner.MainActivityListView.tasksListAdapter;

public class DescriptionOfTask extends AppCompatActivity {
    TextView dateOfTaskTV;
    TextView timeOfTaskTV;
    TextView nameOfTaskTV;
    TextView descriptionOfTaskTV;
    LinearLayout dateLayout;
    LinearLayout timeLayout;
    ImageView statusIcon;
    Animation fabOpen;
    Animation fabClose;
    Animation rotateClockWise;
    Animation rotateAnticlockwise;
    boolean isFabMenuOpened = false;
    FloatingActionButton fabMainButton;
    FloatingActionButton fabEdit;
    FloatingActionButton fabDelete;
    FloatingActionButton fabMarkAsFirst;
    FloatingActionButton fabMarkAsSecond;
    TextView tvEditFab;
    TextView tvDeleteFab;
    TextView tvMarkAsFirstFab;
    TextView tvMarkAsSecondFab;
    Task task;
    int id;
    static public ArrayList<Task> tasksList;
    static public boolean isDescriptionOfTaskOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_of_task);
        initFabMenu();
        getAndSetTaskInfo();
        isDescriptionOfTaskOpened = true;
    }

    public void getAndSetTaskInfo() {
        id = getIntent().getExtras().getInt("id");
        task = tasksList.get(id);
        String nameOfTask = task.getName();
        String dateOfTask = task.getDate();
        String timeOfTask = task.getTime();
        String descriptionOfTask = task.getDescription();
        String statusOfTask = task.getStatusToString();
        dateOfTaskTV = (TextView) findViewById(R.id.descriptionActivityDateOfTaskTV);
        timeOfTaskTV = (TextView) findViewById(R.id.descriptionActiviyTimeOfTaskTV);
        nameOfTaskTV = (TextView) findViewById(R.id.descriptionActivityNameOfTaskTV);
        descriptionOfTaskTV = (TextView) findViewById(R.id.descriptionActivityDescriptionOfTaskTV);
        dateLayout = (LinearLayout) findViewById(R.id.descriptionActivityDateLayout);
        timeLayout = (LinearLayout) findViewById(R.id.descriptionActivityTimeLayout);
        statusIcon = (ImageView) findViewById(R.id.descriptionActivityItemStatusIcon);
        dateOfTaskTV.setText(dateOfTask);
        timeOfTaskTV.setText(timeOfTask);
        nameOfTaskTV.setText(nameOfTask);
        descriptionOfTaskTV.setText(descriptionOfTask);
        checkStatusOfTak();
        if (dateOfTask == null || dateOfTask.equals("")) {
            dateLayout.setVisibility(View.GONE);
        }
        if (timeOfTask == null || timeOfTask.equals("")) {
            timeLayout.setVisibility(View.GONE);
        }
        if (statusOfTask.equals(DBHelper.STATUS_COMPLETED)) {
            statusIcon.setBackgroundResource(R.mipmap.new_completed);
        }
        if (statusOfTask.equals(DBHelper.STATUS_IN_PROGRESS)) {
            statusIcon.setBackgroundResource(R.mipmap.new_in_process);
        }
        if (statusOfTask.equals(DBHelper.STATUS_UNCOMPLETED)) {
            statusIcon.setBackgroundResource(R.mipmap.new_uncompleted);
        }
    }

    private void checkStatusOfTak() {
        if (task.getStatus() == Task.Status.IN_PROGRESS) {
            tvMarkAsFirstFab.setText(R.string.mark_as_completed);
            fabMarkAsFirst.setImageResource(R.drawable.ic_done_white_24dp);
            tvMarkAsSecondFab.setText(R.string.mark_as_uncompleted);
            fabMarkAsSecond.setImageResource(R.drawable.ic_clear_white_24dp);
        }
        if (task.getStatus() == Task.Status.COMPLETED) {
            tvMarkAsFirstFab.setText(R.string.mark_as_in_progress);
            fabMarkAsFirst.setImageResource(R.drawable.ic_cached_white_24dp);
            tvMarkAsSecondFab.setText(R.string.mark_as_uncompleted);
            fabMarkAsSecond.setImageResource(R.drawable.ic_clear_white_24dp);
        }
        if (task.getStatus() == Task.Status.UNCOMPLETED) {
            tvMarkAsFirstFab.setText(R.string.mark_as_completed);
            fabMarkAsFirst.setImageResource(R.drawable.ic_done_white_24dp);
            tvMarkAsSecondFab.setText(R.string.mark_as_in_progress);
            fabMarkAsSecond.setImageResource(R.drawable.ic_cached_white_24dp);
        }
    }

    public void initFabMenu() {
        fabMainButton = (FloatingActionButton) findViewById(R.id.fab_main_description_activity);
        fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete);
        fabMarkAsFirst = (FloatingActionButton) findViewById(R.id.fab_mark_as_first);
        fabMarkAsSecond = (FloatingActionButton) findViewById(R.id.fab_mark_as_second);
        tvEditFab = (TextView) findViewById(R.id.tv_edit_fab);
        tvDeleteFab = (TextView) findViewById(R.id.tv_delete_fab);
        tvMarkAsFirstFab = (TextView) findViewById(R.id.tv_mark_as_first_fab);
        tvMarkAsSecondFab = (TextView) findViewById(R.id.tv_mark_as_second_fab);
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise);
        rotateClockWise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        fabEdit.setClickable(false);
        fabDelete.setClickable(false);
        fabMarkAsFirst.setClickable(false);
        fabMarkAsSecond.setClickable(false);
        tvEditFab.setClickable(false);
        tvDeleteFab.setClickable(false);
        tvMarkAsFirstFab.setClickable(false);
        tvMarkAsSecondFab.setClickable(false);
        fabEdit.setVisibility(View.GONE);
        fabDelete.setVisibility(View.GONE);
        fabMarkAsFirst.setVisibility(View.GONE);
        fabMarkAsSecond.setVisibility(View.GONE);
        tvEditFab.setVisibility(View.GONE);
        tvDeleteFab.setVisibility(View.GONE);
        tvMarkAsFirstFab.setVisibility(View.GONE);
        tvMarkAsSecondFab.setVisibility(View.GONE);
        isFabMenuOpened = false;
    }

    public void onFabButtonClick(View view) {
        switch (view.getId()) {
            case R.id.fab_main_description_activity:
                if (isFabMenuOpened) {
                    ViewCompat.animate(fabMainButton).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
                    fabEdit.startAnimation(fabClose);
                    fabDelete.startAnimation(fabClose);
                    fabMarkAsFirst.startAnimation(fabClose);
                    fabMarkAsSecond.startAnimation(fabClose);
                    tvEditFab.startAnimation(fabClose);
                    tvDeleteFab.startAnimation(fabClose);
                    tvMarkAsFirstFab.startAnimation(fabClose);
                    tvMarkAsSecondFab.startAnimation(fabClose);
                    fabEdit.setClickable(false);
                    fabDelete.setClickable(false);
                    fabMarkAsFirst.setClickable(false);
                    fabMarkAsSecond.setClickable(false);
                    tvEditFab.setClickable(false);
                    tvDeleteFab.setClickable(false);
                    tvMarkAsFirstFab.setClickable(false);
                    tvMarkAsSecondFab.setClickable(false);
                    isFabMenuOpened = false;
                } else {
                    ViewCompat.animate(fabMainButton).rotation(90.0F).withLayer().setDuration(300).setInterpolator(new LinearInterpolator()).start();
                    fabEdit.startAnimation(fabOpen);
                    fabDelete.startAnimation(fabOpen);
                    fabMarkAsFirst.startAnimation(fabOpen);
                    fabMarkAsSecond.startAnimation(fabOpen);
                    tvEditFab.startAnimation(fabOpen);
                    tvDeleteFab.startAnimation(fabOpen);
                    tvMarkAsFirstFab.startAnimation(fabOpen);
                    tvMarkAsSecondFab.startAnimation(fabOpen);
                    fabEdit.setClickable(true);
                    fabDelete.setClickable(true);
                    fabMarkAsFirst.setClickable(true);
                    fabMarkAsSecond.setClickable(true);
                    tvEditFab.setClickable(true);
                    tvDeleteFab.setClickable(true);
                    tvMarkAsFirstFab.setClickable(true);
                    tvMarkAsSecondFab.setClickable(true);
                    fabEdit.setVisibility(View.VISIBLE);
                    fabDelete.setVisibility(View.VISIBLE);
                    fabMarkAsFirst.setVisibility(View.VISIBLE);
                    fabMarkAsSecond.setVisibility(View.VISIBLE);
                    tvEditFab.setVisibility(View.VISIBLE);
                    tvDeleteFab.setVisibility(View.VISIBLE);
                    tvMarkAsFirstFab.setVisibility(View.VISIBLE);
                    tvMarkAsSecondFab.setVisibility(View.VISIBLE);
                    isFabMenuOpened = true;
                }
                break;
            case R.id.tv_mark_as_first_fab:
            case R.id.fab_mark_as_first:
                markAs(tvMarkAsFirstFab);
                break;
            case R.id.tv_mark_as_second_fab:
            case R.id.fab_mark_as_second:
                markAs(tvMarkAsSecondFab);
                break;
            case R.id.fab_delete:
            case R.id.tv_delete_fab:
                tasksList.remove(id);
                MainActivityListView.dbQueryManager.insertTasksToDB();
                MainActivityListView.dbQueryManager.readTasksFromDB();
                tasksListAdapter.notifyDataSetChanged();
                finish();
                break;
            case R.id.fab_edit:
            case R.id.tv_edit_fab:
                AddOrEditTaskActivity.isAddTaskSelected = false;
                AddOrEditTaskActivity.idOfTask = id;
                AddOrEditTaskActivity.tasksList = tasksList;
                Intent openAddOrEditTaskActivity = new Intent(this, AddOrEditTaskActivity.class);
                this.startActivity(openAddOrEditTaskActivity);
                finish();
                break;
        }
    }

    public void markAs(TextView textView) {
        if (textView.getText().equals(getString(R.string.mark_as_completed))) {
            id = tasksList.indexOf(task);
            tasksList.get(id).setStatus(Task.Status.COMPLETED);
            task = tasksList.get(id);
            statusIcon.setBackgroundResource(R.mipmap.new_completed);
            tasksList = MainActivityListView.completedTasksList;
        }
        if (textView.getText().equals(getString(R.string.mark_as_uncompleted))) {
            id = tasksList.indexOf(task);
            tasksList.get(id).setStatus(Task.Status.UNCOMPLETED);
            task = tasksList.get(id);
            statusIcon.setBackgroundResource(R.mipmap.new_uncompleted);
            tasksList = MainActivityListView.uncompletedTasksList;
        }
        if (textView.getText().equals(getString(R.string.mark_as_in_progress))) {
            id = tasksList.indexOf(task);
            tasksList.get(id).setStatus(Task.Status.IN_PROGRESS);
            task = tasksList.get(id);
            statusIcon.setBackgroundResource(R.mipmap.new_in_process);
            tasksList = MainActivityListView.uncompletedTasksList;
        }
        MainActivityListView.dbQueryManager.insertTasksToDB();
        MainActivityListView.dbQueryManager.readTasksFromDB();
        tasksListAdapter.notifyDataSetChanged();
        checkStatusOfTak();
    }
}
