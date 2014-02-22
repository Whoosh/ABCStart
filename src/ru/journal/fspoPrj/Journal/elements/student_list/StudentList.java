package ru.journal.fspoPrj.journal.elements.student_list;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

public class StudentList extends ScrollView {

    private static final String STUDENTS_KEY = "s_t_k";

    private Context context;
    private LinearLayout studentsList;
    private Student[] students;

    public StudentList(Context context) {
        super(context);
        this.context = context;
        studentsList = new LinearLayout(context);
        studentsList.setOrientation(LinearLayout.VERTICAL);
    }

    public void setStudents(Student[] students) {
        this.students = students;
        super.removeAllViews();
        studentsList.removeAllViews();
        studentsList.addView(new HorizontalLine(context, Color.BLACK));
        for (int i = 0; i < students.length - 1; i++) {
            studentsList.addView(new StudentElement(context, students[i].getShortName()));
            studentsList.addView(new HorizontalLine(context, Color.BLACK));
        }
        studentsList.addView(new StudentElement(context, students[(students.length - 1)].getShortName()));
        studentsList.addView(new HorizontalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        super.addView(studentsList);
    }

    public void restoreState(Student[] students) {
        if (students != null) {
            setStudents(students);
        }
    }

    public void dropStudents() {
        removeAllViews();
    }
}
