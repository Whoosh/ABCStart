package ru.journal.fspoPrj.journal.callbacks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.SemesterButton;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class LessonsSelector implements ActionMode.Callback {

    private static final String EMPTY = "";
    private static final int DATE_BUTTON_ID = 102;
    public static final int POSSIBLY_SELECTED_LESSONS = 1;

    private JournalActivity parent;
    private ActionMode actionMode;
    private GroupLesson[] lessonList;
    private SemesterButton semesterButton;
    private LessonSelectedCallBack selectedCallBack;

    private String lastLessonTitle = "";

    public LessonsSelector(JournalActivity parent) {
        this.parent = parent;
        this.selectedCallBack = parent;
    }

    public void setSemesterButton(SemesterButton semesterButton) {
        this.semesterButton = semesterButton;
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

    public void setLessonTitle(GroupLesson lesson) {
        lastLessonTitle = getSelectedLesson((lesson != null) ? lesson.getShortName() : EMPTY);
        actionMode.invalidate();
    }

    public String getSelectedLesson(String currentSelectedLesson) {
        try {
            for (GroupLesson lesson : lessonList) {
                if (lesson.getShortName().equals(currentSelectedLesson)) {
                    return currentSelectedLesson;
                }
            }
            return lessonList[0].getShortName();
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
            return currentSelectedLesson;
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
        initNewMenu(menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() != DATE_BUTTON_ID) {
            for (GroupLesson lesson : lessonList) {
                if (lesson.getShortName().equals(menuItem.getTitle().toString())) {
                    selectedCallBack.lessonSelected(lesson);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parent.onBackPressed();
    }

    private void initNewMenu(Menu menu) {
        actionMode.setCustomView(new SerifTextView(parent, lastLessonTitle));
        if (lessonList != null) {
            menu.add(Menu.NONE, DATE_BUTTON_ID, Menu.NONE, EMPTY).setActionView(semesterButton);
            if (lessonList.length > POSSIBLY_SELECTED_LESSONS) {
                for (GroupLesson lesson : lessonList) {
                    menu.add(lesson.getShortName());
                }
            }
        }
    }

    public static interface LessonSelectedCallBack {
        void lessonSelected(GroupLesson lesson);
    }
}

