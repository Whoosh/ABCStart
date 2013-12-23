package com.example.First_prj.Journal.MainTable;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.HorizontalLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.Journal.CustomViewElements.EvaluationCell;

public class TableWithMarks extends HorizontalScrollView implements View.OnTouchListener {

    private int numOfPeople;
    private int maxDateRange;
    private Context context;
    private int elementSize;

    public TableWithMarks(Context context, int numOfPeople, int maxDateRange) {
        super(context);
        this.maxDateRange = maxDateRange;
        this.numOfPeople = numOfPeople;
        this.context = context;
        elementSize = (int) (context.getResources().getDisplayMetrics().density * 50);
        initMatrix();
        super.setOnTouchListener(this);

    }

    private void initMatrix() {
        LinearLayout rows = new LinearLayout(context);
        LinearLayout columns = new LinearLayout(context);
        columns.setOrientation(LinearLayout.VERTICAL);
        EvaluationCell element;
        for (int i = 0; i < numOfPeople; i++) {
            for (int j = 0; j < maxDateRange; j++) {
                element = new EvaluationCell(context, j, i, elementSize);
                rows.addView(element);
                rows.addView(new VerticalLine(context, Color.BLACK));
            }
            columns.addView(rows);
            columns.addView(new HorizontalLine(context, Color.BLACK));
            rows = new LinearLayout(context);
        }

        super.addView(columns);
    }

    public int getElementSize() {
        return elementSize;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true; // блочим обрывание евента от родителя.
    }
}
