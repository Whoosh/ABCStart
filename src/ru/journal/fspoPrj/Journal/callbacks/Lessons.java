package ru.journal.fspoPrj.journal.callbacks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

import java.util.ArrayList;

public class Lessons implements ActionMode.Callback {

    ArrayList<String> lessonList;

    public Lessons(ArrayList<String> lessonsList) {
        this.lessonList = lessonsList;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        for (String lesson : lessonList) {
            menu.add(lesson);
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        LookingJournalActivity.lessonSelector.setDefaultText();
        actionMode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}

