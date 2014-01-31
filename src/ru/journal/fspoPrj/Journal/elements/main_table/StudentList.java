package ru.journal.fspoPrj.journal.elements.main_table;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

import java.util.ArrayList;

public class StudentList extends LinearLayout {

    public StudentList(Context context, ArrayList<String> students) {
        super(context);
        super.setOrientation(VERTICAL);
        super.setBackgroundColor(Config.getBackgroundColor());
        super.setLayoutParams(new ViewGroup.LayoutParams(
                        Config.getStudentListElementsWight(context),
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        addStudents(context, students);
        super.setBackgroundColor(Config.getStudentListBackgroundColor());
    }

    public void addStudents(Context context, ArrayList<String> students) {
        for (String student : students) {
            super.addView(new StudentElement(context,student));
            super.addView(new HorizontalLine(context, Config.getSeparateLineColor()));
        }
    }

    private class StudentElement extends FrameLayout {

        public StudentElement(Context context,String student) {
            super(context);
            super.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    Config.getStudentListElementHeight()));
            super.addView(new SerifTextView(context, student, GlobalConfig.getHeaderTextSize()));
        }
    }


}
