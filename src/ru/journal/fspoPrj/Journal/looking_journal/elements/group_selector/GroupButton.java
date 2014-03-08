package ru.journal.fspoPrj.journal.looking_journal.elements.group_selector;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;

public class GroupButton extends Button {

    private static final String DEFAULT_TITLE = "Группа ";
    private static final String REFRESH = "Обновить";

    private static final int UPPED_COLOR = Color.parseColor("#E8E8E8");
    private static final int PRESSED_COLOR = Color.parseColor("#C1E6DE");

    private static String selectedGroup = "";

    public GroupButton(Context context) {
        super(context);
        setBackgroundColor(UPPED_COLOR);
        if (selectedGroup.isEmpty()) {
            selectedGroup = DEFAULT_TITLE;
        }
        setText(selectedGroup);
        setTextSize(Config.getSelectorGroupTextSize());
        setLayoutParams(new LinearLayout.LayoutParams(Config.getSelectorGroupOrLabelWidth(), Config.getSelectorGroupOrLabelHeight()));
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

    public void removeGroupTitle() {
        selectedGroup = DEFAULT_TITLE;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case KeyEvent.ACTION_UP: {
                setBackgroundColor(UPPED_COLOR);
            }
            break;
            case KeyEvent.ACTION_DOWN:
            case KeyEvent.ACTION_MULTIPLE:
                setBackgroundColor(PRESSED_COLOR);
        }
        return super.onTouchEvent(event);
    }
}
