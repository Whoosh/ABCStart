package ru.journal.fspoPrj.journal.elements.main_table;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.elements.custom_cell.EvolutionCell;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

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
        super.removeAllViews();
        scroller.removeAllViews();
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
        rowStack.addView(new HorizontalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        scroller.addView(rowStack);
        super.addView(scroller);
        super.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
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
