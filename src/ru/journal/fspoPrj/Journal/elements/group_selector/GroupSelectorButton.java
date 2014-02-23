package ru.journal.fspoPrj.journal.elements.group_selector;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.Group;

public class GroupSelectorButton extends Button {

    private static final String DEFAULT_TITLE = "Группа ";
    private static final String REFRESH = "Обновить";

    private static String selectedGroup = "";

    public GroupSelectorButton(Context context) {
        super(context);
        if (selectedGroup.isEmpty()) {
            selectedGroup = DEFAULT_TITLE;
        }
        setText(selectedGroup);
        setTextSize(Config.getSelectorGroupTextSize());
        setLayoutParams(new LinearLayout.LayoutParams(Config.getSelectorGroupWidth(), Config.getSelectorGroupHeight()));
    }

    public void setSelectedGroup(Group group) {
        selectedGroup = group.getStringGroupNumber();
        setText(selectedGroup);
    }

    public void setSelectedGroup(String groupNumber) {
        selectedGroup = groupNumber;
        setText(selectedGroup);
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
