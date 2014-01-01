package ru.journal.fspoPrj.journal.callbacks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

import java.util.ArrayList;

public class Lessons implements ActionMode.Callback {

    public Lessons(ArrayList<String> lessonsList) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        for (int i = 0; i < 100; i++) {
            //@TODO тестовый вариант.
            menu.add("Список предметов");
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        LookingJournalActivity.lessonSelector.setLessonName("Hello");
        actionMode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}

