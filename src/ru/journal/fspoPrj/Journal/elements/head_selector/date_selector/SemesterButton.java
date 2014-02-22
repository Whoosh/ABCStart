package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class SemesterButton extends Button {

    private int selectedSemester = 1;

    public static final int FIRST = 1;
    public static final int LAST = 2;

    public SemesterButton(Context context) {
        super(context);
        setSelectedSemester(selectedSemester);
    }

    public void setSelectedSemester(int semester) {
        this.selectedSemester = semester;
        setText((semester & 1) == 0 ? SemesterSelector.LAST : SemesterSelector.FIRST);
    }

    public void saveState(Bundle outState) {
        outState.putInt(getClass().getCanonicalName(), selectedSemester);
    }

    public void restoreState(Bundle savedInstanceState) {
        setSelectedSemester(savedInstanceState.getInt(getClass().getCanonicalName()));
    }
}
