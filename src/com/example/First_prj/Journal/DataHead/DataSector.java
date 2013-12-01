package com.example.First_prj.Journal.DataHead;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Gradients.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;

public class DataSector extends LinearLayout {

    private static int defaultWight;

    public DataSector(Context context, int numOfMonth) {
        super(context);
        super.setOrientation(VERTICAL);
        defaultWight = ((int) (100 * context.getResources().getDisplayMetrics().density));
        super.setLayoutParams(new ViewGroup.LayoutParams(defaultWight, ViewGroup.LayoutParams.WRAP_CONTENT));
        super.setGravity(Gravity.CENTER);
        super.addView(new SerifTextView(context, Constants.Month.getMonth(numOfMonth), Constants.DEFAULT_TEXT_SIZE));
        super.addView(new BubbleHorizontalGradientLine(context, Constants.ONE));
        super.addView(new SerifTextView(context, addDataYear(numOfMonth), Constants.DEFAULT_TEXT_SIZE));
    }

    private String addDataYear(int numOfMonth) {
        return Integer.toString(2012 + numOfMonth / 12, 10);
    }

    //@TODO сделать без статик
    public static int getDefaultWight() {
        return defaultWight;
    }


}
