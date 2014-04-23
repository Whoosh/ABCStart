package ru.journal.fspoPrj.journal.looking_journal.elements.student_list;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.util.ArrayList;

public class StudentList extends ScrollView {

    private Context context;
    private LinearLayout studentsList;

    public StudentList(Context context) {
        super(context);
        this.context = context;
        studentsList = new LinearLayout(context);
        studentsList.setOrientation(LinearLayout.VERTICAL);
    }

    public void setStudents(ArrayList<Student> students) {
        super.removeAllViews();
        studentsList.removeAllViews();
        studentsList.addView(new HorizontalLine(context, Color.BLACK));
        for (int i = 0; i < students.size() - 1; i++) {
            studentsList.addView(new StudentElement(context, students.get(i).getShortName()));
            studentsList.addView(new HorizontalLine(context, Color.BLACK));
        }
        studentsList.addView(new StudentElement(context, students.get((students.size() - 1)).getShortName()));
        studentsList.addView(new HorizontalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        super.addView(studentsList);
    }

    public void restoreState(ArrayList<Student> students) {
        if (students != null) {
            setStudents(students);
        }
    }


    public void setState(EditJournalsCommunicator jC) {
        setStudents(jC.getStudents());
    }
}
