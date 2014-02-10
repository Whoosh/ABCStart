package ru.journal.fspoPrj.journal.elements.student_list;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

import java.util.ArrayList;

public class StudentList extends ScrollView {

    private Context context;
    private LinearLayout studentStorage;

    public StudentList(Context context) {
        super(context);
        this.context = context;
        studentStorage = new LinearLayout(context);
        studentStorage.setOrientation(LinearLayout.VERTICAL);
    }

    public void setStudents(ArrayList<String> students) {
        studentStorage.removeAllViews();
        studentStorage.addView(new HorizontalLine(context, Color.BLACK));
        for (int i = 0; i < students.size() - 1; i++) {
            studentStorage.addView(new StudentElement(context, students.get(i)));
            studentStorage.addView(new HorizontalLine(context, Color.BLACK));
        }
        studentStorage.addView(new StudentElement(context, students.get(students.size() - 1)));
        studentStorage.addView(new HorizontalLine(context, Color.CYAN, 5)); // TODO
        super.addView(studentStorage);
    }
}
