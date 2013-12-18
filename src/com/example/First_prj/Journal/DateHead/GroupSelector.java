package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.GlobalConstants;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;

public class GroupSelector extends HorizontalScrollView implements View.OnClickListener {

    private static final byte GROUP_COUNT = 24;

    private SerifTextView[] groups;
    private String currentGroup = "113"; // стартовая группа
    private int oldPosition;
    private int oldIndexPosition;

    public GroupSelector(Context context) {
        super(context);
        super.setBackgroundColor(Color.argb(100,1,81,90));
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LinearLayout groupsBuffer = new LinearLayout(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2,
                (int) (50 * context.getResources().getDisplayMetrics().density)));
        super.setHorizontalScrollBarEnabled(false);

        groups = new SerifTextView[GROUP_COUNT];
        // все группы с 113 по 463
        for (int i = 100, k = 0; i < 401; i += 100)
            for (int j = 10; j < 64; j += 10, k++) {
                groups[k] = new SerifTextView(context, " " + (i + j + 3) + " ", 20);
                groupsBuffer.addView(groups[k]);
                groupsBuffer.addView(new VerticalLine(context, Color.DKGRAY, GlobalConstants.ONE));
                groups[k].setOnClickListener(this);
            }
        refresh();
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

    //@TODO при глобальном рефакторинге, вывести в отдельный класс..
    // Ад
    private void setFocusToDate() {
        try {
            super.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            post(new Runnable() {
                                public void run() {
                                    scrollTo(oldPosition, 0);
                                }
                            });
                        }
                    });
        } catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }


    private int getOldFocusIndex() {
        for (int i = 0; i < groups.length; i++)
            if (groups[i].getStringText().equals(currentGroup))
                return i;
        return 0;
    }

    public void refresh() {
        oldIndexPosition = getOldFocusIndex();
        oldPosition = oldIndexPosition * groups[0].getCurrentWight();
        if (oldIndexPosition != 0)
            setFocusToDate();
        for (int i = 0; i < groups.length; i++)
            groups[i].setBackgroundColor(Color.TRANSPARENT);
        groups[oldIndexPosition].setBackgroundColor(Color.DKGRAY);
    }

}
