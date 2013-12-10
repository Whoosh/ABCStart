package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.HorizontalLine;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.VerticalLine;

public class DateLessonsLine extends LinearLayout {

    private int maxDateRange;
    private Context context;
    private LinearLayout[] dateElements;
    private int elementSize;

    public DateLessonsLine(Context context, int maxDateRange) {
        super(context);
        this.context = context;
        this.maxDateRange = maxDateRange;
        super.setOrientation(HORIZONTAL);
        dateElements = new LinearLayout[maxDateRange];
        elementSize = (int) (50 * context.getResources().getDisplayMetrics().density);

        for (int i = 0; i < maxDateRange; i++) {
            dateElements[i] = new LinearLayout(context);
            dateElements[i].setGravity(Gravity.CENTER);
            dateElements[i].setLayoutParams(new ViewGroup.LayoutParams(elementSize, elementSize));
            dateElements[i].addView(new SerifTextView(context, "" + i, Constants.DEFAULT_TEXT_SIZE));
            super.addView(dateElements[i]);
            super.addView(new VerticalLine(context, Color.CYAN,1));
        }
    }


}
