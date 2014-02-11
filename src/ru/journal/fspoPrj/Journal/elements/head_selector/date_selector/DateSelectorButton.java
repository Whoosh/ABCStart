package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

public class DateSelectorButton extends Button {

    private static final String DEFAULT_VALUE = "Дата";

    private String selectedDate;

    public DateSelectorButton(Context context) {
        super(context);
        setSelectedDate(DEFAULT_VALUE);
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        setText(selectedDate);
    }

    public void saveState(Bundle outState) {
        outState.putString(getClass().getCanonicalName(), selectedDate);
    }

    public void restoreState(Bundle savedInstanceState) {
        setSelectedDate(savedInstanceState.getString(getClass().getCanonicalName()));
    }
}
