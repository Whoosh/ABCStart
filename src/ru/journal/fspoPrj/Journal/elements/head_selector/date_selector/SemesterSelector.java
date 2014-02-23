package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

public class SemesterSelector extends LinearLayout implements View.OnClickListener {

    public static final String FIRST = "Семестр 1";
    public static final String LAST = "Семестр 2";

    private SemesterSelectorDialog semesterSelectorDialog;
    private SemesterSelector.semesterCallBack callBack;

    public SemesterSelector(Activity parent, SemesterSelectorDialog semesterSelectorDialog, SemesterSelector.semesterCallBack callBack) {
        super(parent);
        this.semesterSelectorDialog = semesterSelectorDialog;
        this.callBack = callBack;

        Button first = new Button(parent);
        Button last = new Button(parent);
        first.setText(FIRST);
        last.setText(LAST);
        first.setOnClickListener(this);
        last.setOnClickListener(this);
        super.addView(first);
        super.addView(last);
    }

    @Override
    public void onClick(View view) {
        callBack.semesterSelected(((Button) view).getText().toString().equals(FIRST) ? 1 : 2);
        semesterSelectorDialog.dismiss();
    }

    public static interface semesterCallBack {
        void semesterSelected(int semester);
    }
}
