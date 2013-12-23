package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.GlobalConfig;

public class DateList extends HorizontalScrollView {

    private int maxDateRange;
    private Context context;
    private LinearLayout[] dateElements;
    private int elementSize;

    // TODO add,set,ect...
    public DateList(Context context, int maxDateRange) {
        super(context);
        this.context = context;
        this.maxDateRange = maxDateRange;
        LinearLayout elements = new LinearLayout(context);
        super.setBackgroundColor(GlobalConfig.LookingJournalConfig.getBackgroundColor());
        dateElements = new LinearLayout[maxDateRange];

        elementSize = (int) (50 * context.getResources().getDisplayMetrics().density);

        for (int i = 0; i < maxDateRange; i++) {
            dateElements[i] = new LinearLayout(context);
            dateElements[i].setGravity(Gravity.CENTER);
            dateElements[i].setLayoutParams(new ViewGroup.LayoutParams(elementSize, elementSize));
            dateElements[i].addView(new SerifTextView(context, "" + i));
            elements.addView(dateElements[i]);
            elements.addView(new VerticalLine(context, GlobalConfig.LookingJournalConfig.getSeparateLineColor()));
        }

        super.addView(elements);
    }

}
