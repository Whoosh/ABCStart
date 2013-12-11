package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.HorizontalLine;
import com.example.First_prj.ForAllCode.SerifTextView;

public class StudentList extends LinearLayout {


    private SerifTextView[] students;
    private Context context;

    private int elementHeight;

    public StudentList(Context context) {
        super(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        elementHeight = (int) (50 * context.getResources().getDisplayMetrics().density);
        this.context = context;
        super.setOrientation(VERTICAL);
        super.setBackgroundColor(Color.argb(100, 1, 81, 90));
        super.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addStudents(int count) { // TODO список
        students = new SerifTextView[count];
        for (int i = 0; i < count; i++) {
            students[i] = new SerifTextView(context, "Какой то студент А.А", 19);
            students[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, elementHeight));
            super.addView(students[i]);
            super.addView(new HorizontalLine(context, Color.CYAN, 1));
        }

    }

}
