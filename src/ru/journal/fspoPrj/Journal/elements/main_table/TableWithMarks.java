package ru.journal.fspoPrj.journal.elements.main_table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.journal.elements.custom_cell.EvolutionCell;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.util.HashMap;

public class TableWithMarks extends LinearLayout {

    private Context context;
    private MScrollView scroller;
    private Student[] students;
    private HashMap<Integer, Visit[]> visits;
    private LightExercisesInfo[] lightExercisesInfo;
    private EvolutionCell[][] matrix;
    private OnClickListener shortListener;
    private OnLongClickListener longClickListener;

    private View rightSideMenu;

    public TableWithMarks(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(Color.BLACK);
        scroller = new MScrollView(context);
    }


    public void createTable(LightVisits lightVisits, Student[] students) {
        super.removeAllViews();
        scroller.removeAllViews();

        this.lightExercisesInfo = lightVisits.getExercisesInfo();
        this.visits = lightVisits.getStudentVisits();
        this.students = students;

        initMatrix();
    }

    private void initMatrix() {
        LinearLayout row = new LinearLayout(context);
        LinearLayout rowStack = new LinearLayout(context);
        rowStack.setOrientation(LinearLayout.VERTICAL);

        int rowsCount = students.length;
        int columnCount = lightExercisesInfo.length;

        matrix = new EvolutionCell[rowsCount][columnCount];

        LightExercisesInfo.TypeState[] states = LightExercisesInfo.TypeState.values();

        for (int i = 0; i < rowsCount; i++) {

            Visit[] sVisits = visits.get(students[i].getIntegerID());

            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = new EvolutionCell(context, sVisits[j], states[lightExercisesInfo[j].getType()]);
                setListeners(matrix[i][j]);
                row.addView(matrix[i][j]);
            }

            rowStack.addView(row);
            row = new LinearLayout(context);
        }

        rowStack.addView(new HorizontalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        scroller.addView(rowStack);
        super.addView(scroller);
        super.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));

        if (rightSideMenu != null) {
            super.addView(rightSideMenu);
        }
    }

    private void setListeners(EvolutionCell element) {
        if (shortListener != null) {
            element.setOnClickListener(shortListener);
            element.setOnLongClickListener(longClickListener);
        }
    }

    public void scrollScrollerTo(int x, int y) {
        scroller.scrollTo(x, y);
    }

    public int getScrollerY() {
        return scroller.getScrollY();
    }

    public void restoreState(LightVisits lightVisits, Student[] students) {
        createTable(lightVisits, students);
    }

    public void setRightFunctions(View element) {
        this.rightSideMenu = element;
    }

    public void setOnCellsLongClickListener(OnLongClickListener longListener) {
        this.longClickListener = longListener;
    }

    public void setOnCellsShortClickListener(OnClickListener listener) {
        this.shortListener = listener;
    }

    public void setColumnColor(int tableIndex, int color) {
        // TODO
    }
}
