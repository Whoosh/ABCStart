package ru.journal.fspoPrj.journal.elements.group_selector;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;

public class GroupSelectorButton extends Button {

    private static final String DEFAULT_TITLE = "Группа ";

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

    }

    public String getSelectedGroup() {
        return selectedGroup;
    }
}
