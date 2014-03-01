package ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class SemesterButton extends Button {

    private int selectedSemester;

    public SemesterButton(Context context) {
        super(context);
        setSelectedSemester(selectedSemester);
    }

    public void setSelectedSemester(int semester) {
        this.selectedSemester = semester;
        setText(SemesterSelector.SEMESTER + semester);
    }

    public void saveState(Bundle outState) {
        outState.putInt(getClass().getCanonicalName(), selectedSemester);
    }

    public void restoreState(Bundle savedInstanceState) {
        setSelectedSemester(savedInstanceState.getInt(getClass().getCanonicalName()));
    }
}
