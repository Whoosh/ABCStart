package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;

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
        super.setBackgroundColor(Color.argb(80, 1, 81, 90));
        dateElements = new LinearLayout[maxDateRange];

        FrameLayout s = new FrameLayout(context);


        elementSize = (int) (50 * context.getResources().getDisplayMetrics().density);

        for (int i = 0; i < maxDateRange; i++) {
            dateElements[i] = new LinearLayout(context);
            dateElements[i].setGravity(Gravity.CENTER);
            dateElements[i].setLayoutParams(new ViewGroup.LayoutParams(elementSize, elementSize));
            dateElements[i].addView(new SerifTextView(context, "" + i));
            elements.addView(dateElements[i]);
            elements.addView(new VerticalLine(context, Color.CYAN, 1));
        }

        super.addView(elements);
    }

    public DateList(Context context, String[] dates) {
        super(context);
        super.setBackgroundColor(Color.argb(80, 1, 81, 90));
        LinearLayout elements = new LinearLayout(context);
        elementSize = (int) (50 * context.getResources().getDisplayMetrics().density);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(elementSize, elementSize);
        TextView bufferElement;

    }


}
