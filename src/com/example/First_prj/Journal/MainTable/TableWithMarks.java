package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.HorizontalLine;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.VerticalLine;

public class TableWithMarks extends HorizontalScrollView {

    private LinearLayout[] rows;
    private LinearLayout[][] elements;
    private int numOfPeople;
    private int maxDateRange;
    private Context context;
    private int elementSize;

    public TableWithMarks(Context context, int numOfPeople, int maxDateRange) {
        super(context);
        this.maxDateRange = maxDateRange;
        this.numOfPeople = numOfPeople;
        this.context = context;
        elementSize = (int) (50 * context.getResources().getDisplayMetrics().density);
        initMatrix();
    }

    private void initMatrix() {
        rows = new LinearLayout[numOfPeople];
        elements = new LinearLayout[numOfPeople][maxDateRange];
        for (int i = 0; i < numOfPeople; i++) {
            rows[i] = new LinearLayout(context);
            rows[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        for (int i = 0; i < numOfPeople; i++) {
            for (int j = 0; j < maxDateRange; j++) {
                elements[i][j] = new LinearLayout(context);
                elements[i][j].setGravity(Gravity.CENTER);
                elements[i][j].setBackgroundColor(Color.argb(200,1,50,90));
                elements[i][j].setLayoutParams(new ViewGroup.LayoutParams(elementSize, elementSize));
                elements[i][j].addView(new SerifTextView(context, "+", Constants.DEFAULT_TEXT_SIZE));
                rows[i].addView(elements[i][j]);
                rows[i].addView(new VerticalLine(context,Color.CYAN,Constants.ONE));
            }
        }

        LinearLayout results = new LinearLayout(context);
        results.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < numOfPeople; i++) {
                results.addView(rows[i]);
                results.addView(new HorizontalLine(context, Color.CYAN, Constants.ONE));
        }
        super.addView(results);
    }

    public int getElementSize(){
        return elementSize;
    }

}
