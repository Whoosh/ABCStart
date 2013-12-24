package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

//
public class GroupSelector extends HorizontalScrollView implements View.OnClickListener {

    private Runnable movingOffsetAction;
    private static final byte GROUP_COUNT = 24;
    private SerifTextView[] groups;
    private String currentGroup = "113"; // стартовая группа
    private int oldPosition;

    public GroupSelector(Context context) {
        super(context);
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        super.setHorizontalScrollBarEnabled(false);
        final LayoutParams groupElementParams = new LayoutParams(FILL_PARENT, FILL_PARENT);

        LinearLayout groupsBuffer = new LinearLayout(context);
        groupsBuffer.setGravity(Gravity.CENTER_VERTICAL);

        super.setLayoutParams(new LayoutParams(
                ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay().getWidth() / 2, LookingJournalConfig.getDateGroupHeight()));

        groups = new SerifTextView[GROUP_COUNT];
        // все группы с 113 по 463
        for (int i = 100, k = 0; i <= 400; i += 100)
            for (int j = 10; j <= 60; j += 10, k++) {
                groups[k] = new SerifTextView(context, " " + (i + j + 3) + " ", GlobalConfig.HEADER_TEXT_SIZE);
                groups[k].setLayoutParams(groupElementParams);
                groupsBuffer.addView(groups[k]);
                groupsBuffer.addView(new VerticalLine(context, Color.DKGRAY));
                groups[k].setOnClickListener(this);
            }
        refreshStateOfVisualPosition();
        super.addView(groupsBuffer);
    }

    @Override
    public void onClick(View view) {
        for (byte i = 0; i < GROUP_COUNT; i++) {
            if (view.equals(groups[i])) {
                currentGroup = groups[i].getStringText();
                for (byte j = 0; j < GROUP_COUNT; j++)
                    groups[j].setBackgroundColor(Color.TRANSPARENT);
                groups[i].setBackgroundColor(Color.DKGRAY);
                return;
            }
        }
    }

    public String getSelectedGroup() {
        return currentGroup;
    }

    public void setOldSelectedGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    private void setFocusToDate() {

        movingOffsetAction = new Runnable() {
            public void run() {
                scrollTo(oldPosition, 0);
            }
        };

        ViewTreeObserver.OnGlobalLayoutListener mover = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                post(movingOffsetAction);
            }
        };

        super.getViewTreeObserver().addOnGlobalLayoutListener(mover);
    }


    private int getOldFocusIndex() {
        for (int i = 0; i < groups.length; i++)
            if (groups[i].getStringText().equals(currentGroup))
                return i;
        return 0;
    }

    public void refreshStateOfVisualPosition() {
        int oldIndexPosition = getOldFocusIndex();

        oldPosition = (oldIndexPosition - GlobalConfig.ONE) * groups[0].getCurrentWight();
        if (oldIndexPosition > 0) setFocusToDate();
        for (SerifTextView group : groups) group.setBackgroundColor(Color.TRANSPARENT);
        groups[oldIndexPosition].setBackgroundColor(Color.DKGRAY);
    }

}
