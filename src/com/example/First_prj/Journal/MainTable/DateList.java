package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;

import java.util.ArrayList;

public class DateList extends HorizontalScrollView {

    public DateList(Context context, ArrayList<String> dates) {
        super(context);
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        LinearLayout elements = new LinearLayout(context);
        for (String date : dates) {
            elements.addView(new DateElement(context, date));
            elements.addView(new VerticalLine(context, LookingJournalConfig.getSeparateLineColor()));
        }
        super.addView(elements);
    }


    private class DateElement extends FrameLayout {

        public DateElement(Context context, String date) {
            super(context);
            super.setLayoutParams(new ViewGroup.LayoutParams(
                    LookingJournalConfig.getDateCellSize(),
                    LookingJournalConfig.getDateCellSize()));
            super.addView(new SerifTextView(context, date, GlobalConfig.DEFAULT_TEXT_SIZE));
        }
    }
}
