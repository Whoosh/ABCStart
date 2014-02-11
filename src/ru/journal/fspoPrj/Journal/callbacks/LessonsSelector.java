package ru.journal.fspoPrj.journal.callbacks;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.JournalsCommunicator;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;

import java.util.ArrayList;

public class LessonsSelector implements ActionMode.Callback {

    private static final String LESSON_SAVED_KEY = "l_s_k";

    private String[] lessonList;
    private Activity caller;
    private ActionMode actionMode;
    private JournalsCommunicator communicator;

    public LessonsSelector(Activity caller, JournalsCommunicator communicator) {
        this.caller = caller;
        this.communicator = communicator;
    }

    public void setLessons(String[] lessonList) {
        this.lessonList = lessonList;
        actionMode.invalidate();
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.clear();
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

    public void saveState(Bundle outState) {
        outState.putStringArray(LESSON_SAVED_KEY, lessonList);
    }

    public void restoreState(Bundle savedInstanceState) {
        lessonList = savedInstanceState.getStringArray(LESSON_SAVED_KEY);
        if (lessonList != null) {
            setLessons(lessonList);
        }
    }


}

