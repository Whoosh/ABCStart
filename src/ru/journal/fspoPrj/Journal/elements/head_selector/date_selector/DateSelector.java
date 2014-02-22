package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.public_code.Month;

public class DateSelector extends LinearLayout implements View.OnClickListener {

    private static final int DROP_DOWN_COUNT = 2;

    private LookingJournalActivity parent;
    private SemesterSelectorDialog semesterSelectorDialog;
    private LinearLayout row;

    public DateSelector(LookingJournalActivity parent, SemesterSelectorDialog semesterSelectorDialog) {
        super(parent);
        this.semesterSelectorDialog = semesterSelectorDialog;
        this.parent = parent;
        super.setOrientation(VERTICAL);
        row = new LinearLayout(parent);
        for (Month.FistSemester month : Month.FistSemester.values()) {
            initOnScreen(month.getMonth());
        }
        super.addView(row);
        row = new LinearLayout(parent);
        for (int i = 0; i < Month.LastSemester.values().length - DROP_DOWN_COUNT; i++) {
            initOnScreen(Month.LastSemester.values()[i].getMonth());
        }
        super.addView(row);
        row = new LinearLayout(parent);
        for (int i = Month.LastSemester.values().length - DROP_DOWN_COUNT; i < Month.LastSemester.values().length; i++) {
            initOnScreen(Month.LastSemester.values()[i].getMonth());
        }
        super.addView(row);
    }

    @Override
    public void onClick(View view) {
        //parent.semesterSelected(((DateElement) view).getText().toString());
        semesterSelectorDialog.dismiss();
    }

    public void initOnScreen(Month month) {
        DateElement dateElement = new DateElement(parent, month);
        dateElement.setOnClickListener(this);
        row.addView(dateElement);
    }

}
