package ru.journal.fspoPrj.journal.edit_journal.elements.lable;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class LessonLabel extends SerifTextView {

    private String currentLabel = "";

    public LessonLabel(Context context, String text) {
        super(context, text);
        setLayoutParams(new ViewGroup.LayoutParams(Config.getSelectorGroupOrLabelWidth(), Config.getSelectorGroupOrLabelHeight()));
    }

    public void setSelectedLesson(TeacherGroup group) {
        currentLabel = group.getGroupLesson();
        setText(currentLabel);
    }

    public void saveState(Bundle outState) {
        outState.putString(getClass().getCanonicalName(), currentLabel);
    }

    public void restoreState(Bundle savedInstanceState) {
        currentLabel = savedInstanceState.getString(getClass().getCanonicalName());
        setText(currentLabel);
    }
}
