package ru.journal.fspoPrj.journal.edit_journal.elements.grop_selector;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;

public class TeacherGroupElement extends Button{

    private TeacherGroup group;

    public TeacherGroupElement(Context context, TeacherGroup group, View.OnClickListener listener) {
        super(context);
        this.group = group;
        setOnClickListener(listener);
        setGravity(Gravity.CENTER);
        setTextSize(Config.getGroupElementTextSize());
        setText(group.getGroupName());
    }

    public TeacherGroup getGroup() {
        return group;
    }

}
