package ru.journal.fspoPrj.journal.elements.main_table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.elements.custom_cell.EvolutionCell;

public class TableWithMarks extends LinearLayout {

    private int numOfPeople;
    private int maxDateRange;
    private Context context;
    private MScrollView scroller;

    public TableWithMarks(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(Color.BLACK);
        scroller = new MScrollView(context);
    }

    public void createTable(int peopleCount, int dateCount) {
        this.maxDateRange = dateCount;
        this.numOfPeople = peopleCount;
        initMatrix();
    }

    private void initMatrix() {
        LinearLayout row = new LinearLayout(context);
        LinearLayout rowStack = new LinearLayout(context);
        rowStack.setOrientation(LinearLayout.VERTICAL);

        EvolutionCell element;

        for (int i = 0; i < numOfPeople; i++) {
            for (int j = 0; j < maxDateRange; j++) {
                element = new EvolutionCell(context, " ");
                row.addView(element);
            }
            rowStack.addView(row);
            row = new LinearLayout(context);
        }
        scroller.addView(rowStack);
        super.addView(scroller);
    }

    public void scrollScrollerTo(int x, int y) {
        scroller.scrollTo(x, y);
    }

    public int getScrollerY() {
        return scroller.getScrollY();
    }

    public MScrollView getScroller() {
        return scroller;
    }
}
