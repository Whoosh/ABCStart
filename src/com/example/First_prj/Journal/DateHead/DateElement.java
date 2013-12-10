package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Gradients.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;

public class DateElement extends LinearLayout {

    private SerifTextView month;
    private SerifTextView year;
    private int width;


    public DateElement(Context context, int numOfMonth) {
        super(context);
        super.setOrientation(VERTICAL);
        width = ((int) (100 * context.getResources().getDisplayMetrics().density));
        super.setLayoutParams(new ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.FILL_PARENT));
        super.setGravity(Gravity.CENTER);

        month = new SerifTextView(context, Constants.Month.getMonth(numOfMonth), Constants.DEFAULT_TEXT_SIZE);
        month.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        year = new SerifTextView(context, addDataYear(numOfMonth), Constants.DEFAULT_TEXT_SIZE);
        year.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        super.addView(month);
        super.addView(new BubbleHorizontalGradientLine(context, Constants.ONE));
        super.addView(year);
    }

    private String addDataYear(int numOfMonth) {
        return Integer.toString(2012 + numOfMonth / 12, 10);
    }

    public String getYear() {
        return year.getStringText();
    }

    public String getMonth() {
        return month.getStringText();
    }

    public int getDefaultWidth(){
        return width;
    }

}
