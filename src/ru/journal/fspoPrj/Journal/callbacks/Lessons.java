package ru.journal.fspoPrj.journal.callbacks;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Lessons implements ActionMode.Callback, View.OnClickListener {

    private static final String LESSON_SAVED_KEY = "l_s_k";

    private ArrayList<String> lessonList;
    private Activity caller;
    private ActionMode actionMode;
    private Semester semester;
    private LinearLayout customViewShower;

    public Lessons(Activity caller) {
        this.caller = caller;
        customViewShower = new LinearLayout(caller);
        semester = new Semester(caller);

        TextView toDoPointer = new TextView(caller);
        toDoPointer.setText("\t\t Выбор предмета");

        customViewShower.addView(semester);
        customViewShower.addView(toDoPointer);

        semester.setOnClickListener(this);
    }

    public void addNewLessons(ArrayList<String> lessonList) {
        this.lessonList = lessonList;
        actionMode.invalidate();
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        actionMode.setCustomView(customViewShower);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        if (lessonList != null) {
            for (String lesson : lessonList) {
                menu.add(lesson);
            }
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        caller.finish();
    }

    @Override
    public void onClick(View view) {
        semester.click();
    }

    public void saveState(Bundle outState) {
        outState.putStringArrayList(LESSON_SAVED_KEY, lessonList);
        outState.putInt(Semester.SAVED_KEY, semester.getIndexer());
    }

    public void restoreState(Bundle savedInstanceState) {
        semester.setIndexer(savedInstanceState.getInt(Semester.SAVED_KEY));
        lessonList = savedInstanceState.getStringArrayList(LESSON_SAVED_KEY);
        if (lessonList != null) {
            addNewLessons(lessonList);
        }
    }

}

