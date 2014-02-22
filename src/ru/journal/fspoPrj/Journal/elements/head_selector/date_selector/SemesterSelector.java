package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

public class SemesterSelector extends LinearLayout implements View.OnClickListener {

    public static final String FIRST = "Семестр 1";
    public static final String LAST = "Семестр 2";

    private LookingJournalActivity parent;
    private SemesterSelectorDialog semesterSelectorDialog;

    public SemesterSelector(LookingJournalActivity parent, SemesterSelectorDialog semesterSelectorDialog) {
        super(parent);
        this.semesterSelectorDialog = semesterSelectorDialog;
        this.parent = parent;
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
        parent.semesterSelected(((Button) view).getText().toString().equals(FIRST) ? 1 : 2);
        semesterSelectorDialog.dismiss();
    }
}
