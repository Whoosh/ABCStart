package ru.journal.fspoPrj.journal.callbacks;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupLesson;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.DateSelectorButton;

public class LessonsSelector implements ActionMode.Callback {

    private static final String EMPTY = "";
    private static final int DATE_BUTTON_ID = 102;

    private GroupLesson[] lessonList;
    private ActionMode actionMode;
    private LookingJournalActivity parent;
    private LessonSelectedCallBack selectedCallBack;
    private DateSelectorButton dateSelectorButton;

    public LessonsSelector(LookingJournalActivity parent) {
        this.parent = parent;
        this.selectedCallBack = parent.getLessonSelectedCallBack();
    }

    public void setDateSelectorButton(DateSelectorButton dateSelectorButton) {
        this.dateSelectorButton = dateSelectorButton;
    }

    public void setLessons(GroupLesson[] lessonList) {
        this.lessonList = lessonList;
        actionMode.invalidate();
    }

    public void restoreState(GroupLesson[] lessonList) {
        if (lessonList != null) {
            setLessons(lessonList);
        }
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
            menu.add(Menu.NONE, DATE_BUTTON_ID, Menu.NONE, EMPTY).setActionView(dateSelectorButton);
            for (GroupLesson lesson : lessonList) {
                menu.add(lesson.getShortName());
            }
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() != DATE_BUTTON_ID) {
            selectedCallBack.lessonSelected(menuItem.getTitle().toString());
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parent.onBackPressed();
    }

    public static interface LessonSelectedCallBack {
        void lessonSelected(String lesson);

        void dateSelected(String date);
    }
}

