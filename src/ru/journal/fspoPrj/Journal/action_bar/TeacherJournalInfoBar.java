package ru.journal.fspoPrj.journal.action_bar;

import android.app.Activity;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLesson;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class TeacherJournalInfoBar implements ActionMode.Callback, View.OnClickListener {

    private static final int REFRESH_BUTTON_ID = 12;
    private static final String EMPTY = "";
    private static final String REFRESH = "Обновить";
    private ActionMode actionMode;
    private Activity callerParent;
    private TeacherLessons teacherLessons;
    private OnGroupSelected onGroupSelected;
    private EditJournalsCommunicator jC;

    public TeacherJournalInfoBar(Activity callerParent) {
        this.callerParent = callerParent;
        this.onGroupSelected = (OnGroupSelected) callerParent;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.clear();
        if (teacherLessons != null) {
            for (TeacherLesson lesson : teacherLessons.getLessons()) {
                SubMenu subMenu = menu.addSubMenu(lesson.getName() + " - Семестр " + lesson.getSemester());
                for (TeacherGroup group : lesson.getGroups()) {
                    subMenu.add(group.getGroupName()).setActionView(new LessonElement(callerParent, group));
                }
            }
        } else {
            addRefreshButton(menu);
        }
        return true;
    }

    private void addRefreshButton(Menu menu) {
        Button refreshButton = new Button(callerParent);
        refreshButton.setText(REFRESH);
        refreshButton.setOnClickListener(this);
        menu.add(Menu.NONE, REFRESH_BUTTON_ID, Menu.NONE, EMPTY).setActionView(refreshButton);
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getActionView() instanceof LessonElement) {
            onGroupSelected.onGroupSelected(((LessonElement) menuItem.getActionView()).getGroup());
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        callerParent.onBackPressed();
    }

    public void setState(EditJournalsCommunicator jC) {
        this.jC = jC;
        teacherLessons = jC.getTeacherLessons();
        actionMode.invalidate();
    }

    @Override
    public void onClick(View view) {
        jC.resendLastQuery();
    }

    public static interface OnGroupSelected {
        void onGroupSelected(TeacherGroup group);
    }

    private static class LessonElement extends View {
        private TeacherGroup group;

        public LessonElement(Context context, TeacherGroup group) {
            super(context);
            this.group = group;
        }

        public TeacherGroup getGroup() {
            return group;
        }
    }
}
