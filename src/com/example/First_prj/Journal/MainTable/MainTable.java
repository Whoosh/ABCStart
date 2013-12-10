package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.HorizontalLine;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.VerticalLine;

public class MainTable extends ScrollView {
    private Context context;

    String sh = "Ровнодвадцатьпятьитри А.Ф ";
    TableWithMarks tableWithMarks;
    HorizontalScrollView horizontalScrollRightTable;
    LinearLayout namesLayout;
    LinearLayout namesOrLessons[];
    LinearLayout all;
    LinearLayout nameOfLesson;
    private int numOfPeople;
    private int maxDataRange;


    public MainTable(Context context) {
        super(context);
        this.context = context;
        this.numOfPeople = 40;
        this.maxDataRange = 30;

        tableWithMarks = new TableWithMarks(context, numOfPeople, maxDataRange);

        nameOfLesson = new LinearLayout(context);

        namesOrLessons = new LinearLayout[numOfPeople];

        all = new LinearLayout(context);

        horizontalScrollRightTable = new HorizontalScrollView(context);
        namesLayout = new LinearLayout(context);
        namesLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < numOfPeople; i++) {
            namesOrLessons[i] = new LinearLayout(context);
            namesOrLessons[i].setGravity(Gravity.CENTER_HORIZONTAL);
            namesOrLessons[i].setOrientation(LinearLayout.VERTICAL);
            namesOrLessons[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tableWithMarks.getElementSize()));
            namesOrLessons[i].addView(new SerifTextView(context, sh + i, Constants.DEFAULT_TEXT_SIZE));
            namesLayout.addView(namesOrLessons[i]);
            namesLayout.addView(new HorizontalLine(context, Color.CYAN, Constants.ONE));
        }

        horizontalScrollRightTable.addView(tableWithMarks);

        all.setOrientation(LinearLayout.HORIZONTAL);
        all.addView(namesLayout);
        all.addView(new VerticalLine(context, Color.CYAN, 2));
        all.addView(horizontalScrollRightTable);

        super.addView(all);
    }
}
