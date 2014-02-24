package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.Logger;

public class SemesterSelector extends LinearLayout implements View.OnClickListener {

    public static final String SEMESTER = "Семестр ";
    public static final char SPACE = ' ';

    private SemesterSelectorDialog semesterSelectorDialog;
    private SemesterSelector.semesterCallBack callBack;

    public SemesterSelector(Integer[] allPossiblySemesters, Activity parent, SemesterSelectorDialog semesterSelectorDialog) {
        super(parent);
        super.setOrientation(VERTICAL);
        this.semesterSelectorDialog = semesterSelectorDialog;
        this.callBack = (SemesterSelector.semesterCallBack) parent;

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
        semesterSelectorDialog.dismiss();
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
