package ru.journal.fspoPrj.journal.elements.group_selector;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;

public class GroupSelectorButton extends Button {

    private static final String DEFAULT_TITLE = "Группа ";
    private static final String REFRESH = "Обновить";

    private String selectedGroup;

    public GroupSelectorButton(Context context) {
        super(context);
        selectedGroup = DEFAULT_TITLE;
        setText(selectedGroup);
        setTextSize(Config.getSelectorGroupTextSize());
        setLayoutParams(new LinearLayout.LayoutParams(Config.getSelectorGroupWidth(), Config.getSelectorGroupHeight()));
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
        setText(selectedGroup);
    }

    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void disableRefreshState() {
        setText(selectedGroup);
    }

    public void refreshStateON() {
        setText(REFRESH);
    }

    public boolean isRefreshState() {
        return getText().toString().equals(REFRESH);
    }

    public void restoreState(Bundle savedInstanceState) {
        String restoredInfo = savedInstanceState.getString(getClass().getCanonicalName());
        if (restoredInfo.equals(REFRESH)) {
            setText(restoredInfo);
        } else {
            setSelectedGroup(restoredInfo);
        }
    }

    public void saveState(Bundle outState) {
        if (getText().toString().equals(REFRESH)) {
            outState.putString(getClass().getCanonicalName(), REFRESH);
        } else {
            outState.putString(getClass().getCanonicalName(), selectedGroup);
        }
    }
}
