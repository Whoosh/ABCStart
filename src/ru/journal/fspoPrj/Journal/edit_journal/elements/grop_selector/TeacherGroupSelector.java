package ru.journal.fspoPrj.journal.edit_journal.elements.grop_selector;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLesson;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class TeacherGroupSelector extends LinearLayout implements View.OnClickListener {
    // TODO REFACTORING
    private static final String TITLE = "Выбор группы";
    public static final String SEMESTER = "Семестр";
    public static final String TAB = "\t";
    private TeacherGroupDialog groupDialog;
    private TeacherGroupDialog.TeacherGroupSelectedCallBack callBack;
    private Activity caller;
    private TeacherLessons teacherLessons;

    public TeacherGroupSelector(Activity caller, TeacherLessons teacherLesson, TeacherGroupDialog dialog) {
        super(caller);
        this.caller = caller;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        this.teacherLessons = teacherLesson;
        this.groupDialog = dialog;
        this.callBack = (TeacherGroupDialog.TeacherGroupSelectedCallBack) caller;

        addView(new SerifTextView(caller, Gravity.CENTER_VERTICAL, TITLE, Config.getGroupSelectorDialogTitleTextSize()));
        addView(new HorizontalLine(caller, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
        addView(new HorizontalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogTransparentSeparateLineHeight()));
        addLessons();
    }

    private void addLessons() {
        LinearLayout scrollerLayout = new LinearLayout(caller);
        ScrollView scrollView = new ScrollView(caller);
        scrollerLayout.setOrientation(VERTICAL);
        scrollView.addView(scrollerLayout);
        for (TeacherLesson lesson : teacherLessons.getLessons()) {
            scrollerLayout.addView(new SerifTextView(caller, Gravity.CENTER_VERTICAL, lesson.getName()));
            scrollerLayout.addView(new SerifTextView(caller, Gravity.CENTER_VERTICAL, TAB + lesson.getSemester() + TAB + SEMESTER));
            scrollerLayout.addView(new HorizontalLine(caller, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
            for (TeacherGroup group : lesson.getGroups()) {
                TeacherGroupElement element = new TeacherGroupElement(caller, group, this);
                element.setGravity(Gravity.CENTER_VERTICAL);
                scrollerLayout.addView(element);
            }
        }
        super.addView(scrollView);
    }

    @Override
    public void onClick(View view) {
        groupDialog.dismiss();
        callBack.groupSelected(((TeacherGroupElement) view).getGroup());
    }
}
