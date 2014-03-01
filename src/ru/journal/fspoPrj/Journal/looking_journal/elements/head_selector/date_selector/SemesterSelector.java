package ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.public_code.Logger;

public class SemesterSelector extends LinearLayout implements View.OnClickListener {

    public static final String SEMESTER = "Семестр ";
    public static final char SPACE = ' ';

    private SemesterDialog semesterDialog;
    private SemesterSelector.semesterCallBack callBack;

    public SemesterSelector(Integer[] allPossiblySemesters, JournalActivity parent, SemesterDialog semesterDialog) {
        super(parent);
        super.setOrientation(VERTICAL);
        this.semesterDialog = semesterDialog;
        this.callBack = parent;
        for (Integer semester : allPossiblySemesters) {
            Button semButton = new Button(parent);
            semButton.setText(SEMESTER + semester);
            semButton.setOnClickListener(this);
            super.addView(semButton);
        }
    }

    @Override
    public void onClick(View view) {
        callBackSemesterIndex(view);
        semesterDialog.dismiss();
    }

    private void callBackSemesterIndex(View view) {
        String semester = ((Button) view).getText().toString();
        try {
            callBack.semesterSelected(Integer.parseInt(semester.substring(semester.lastIndexOf(SPACE) + 1)));
        } catch (Exception ex) {
            Logger.printError(ex, getClass());
            callBack.semesterSelected(0);
        }
    }

    public static interface semesterCallBack {
        void semesterSelected(int semester);
    }
}
