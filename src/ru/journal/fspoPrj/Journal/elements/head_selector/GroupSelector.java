package ru.journal.fspoPrj.journal.elements.head_selector;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;


public class GroupSelector extends HorizontalScrollView implements View.OnClickListener {

    private static final byte GROUP_COUNT = 24;

    private byte groupIndex;

    private SerifTextView[] groups;

    public GroupSelector(Context context) {
        super(context);
        super.setBackgroundColor(Config.getBackgroundColor());
        super.setHorizontalScrollBarEnabled(false);
        super.setLayoutParams(new LayoutParams(
                Config.getGroupSelectorWidth(context),
                Config.getDateGroupHeight()));
        final LayoutParams groupElementParams = new LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        groups = new SerifTextView[GROUP_COUNT];
        LinearLayout groupsBuffer = new LinearLayout(context);
        groupsBuffer.setBackgroundColor(Config.getGroupSelectorBackgroundColor());

        // все группы с 113 по 463 // TODO запросом, если такой будет
        for (int i = 100, k = 0; i <= 400; i += 100)
            for (int j = 10; j <= 60; j += 10, k++) {
                groups[k] = new SerifTextView(context, " " + (i + j + 3) + " ", GlobalConfig.HEADER_TEXT_SIZE);
                groups[k].setLayoutParams(groupElementParams);
                groups[k].setOnClickListener(this);
                groupsBuffer.addView(groups[k]);
                groupsBuffer.addView(new VerticalLine(context, Color.DKGRAY));
            }
        refreshVisualState();
        super.addView(groupsBuffer);
    }

    @Override
    public void onClick(View view) {
        for (byte i = 0; i < GROUP_COUNT; i++)
            if (view.equals(groups[i])) {
                changeGroupsState(i);
                break;
            }
    }

    private void setFieldsState(byte currentSelectedIndex) {
        groupIndex = currentSelectedIndex;
    }

    public byte getOldFocusedIndex() {
        return groupIndex;
    }

    private String getCurrentSelectedGroup() {
        return groups[groupIndex].getStringText();
    }

    public void moveFocusToActualPosition() {
        post(new CurrentFocusMover());
    }

    public void setOldFocusedIndex(byte focusedIndex) {
        setGroupStatePressedUP();
        this.groupIndex = focusedIndex;
        if (groupIndex > 0) moveFocusToActualPosition();
    }

    public void refreshVisualState() {
        changeGroupsState(groupIndex);
    }

    private void changeGroupsState(byte groupIndex) {
        setGroupStatePressedUP();
        setFieldsState(groupIndex);
        setGroupStatePressedDown();
    }

    private void setGroupStatePressedDown() {
        groups[groupIndex].setBackgroundColor(Config.getPressedGroupColor());
    }

    private void setGroupStatePressedUP() {
        groups[groupIndex].setBackgroundColor(Color.TRANSPARENT);
    }


    public class CurrentFocusMover implements Runnable {
        @Override
        public void run() {
            scrollTo(groupIndex * groups[0].getCurrentWight(), 0);
        }
    }
}