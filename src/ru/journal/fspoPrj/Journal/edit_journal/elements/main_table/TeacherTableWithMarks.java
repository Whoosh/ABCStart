package ru.journal.fspoPrj.journal.edit_journal.elements.main_table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.journal.looking_journal.elements.main_table.MScrollView;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.EvolutionCell;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.util.HashMap;

public class TeacherTableWithMarks extends LinearLayout {

    private Context context;
    private MScrollView scroller;
    private OnClickListener shortListener;
    private OnLongClickListener longClickListener;
    private Student[] students;
    private LightExercisesInfo[] lightExercisesInfo;
    private HashMap<Integer, Visit[]> visits;
    private LightExercisesInfo.TypeState[] states;
    private EvolutionCell[][] matrix;
    private View rightSideMenu;

    public TeacherTableWithMarks(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(Color.BLACK);
        scroller = new MScrollView(context);
        states = LightExercisesInfo.TypeState.values();
    }

    public void createTable(LightVisits lightVisits, Student[] students) {
        super.removeAllViews();
        scroller.removeAllViews();

        this.lightExercisesInfo = lightVisits.getExercisesInfo();
        this.visits = lightVisits.getStudentVisits();
        this.students = students;

        matrix = new EvolutionCell[students.length][lightExercisesInfo.length];
        initMatrix();
    }

    private void initMatrix() {
        LinearLayout rowStack = new LinearLayout(context);
        rowStack.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < students.length; i++) {
            rowStack.addView(createRow(visits.get(students[i].getIntegerID()), i, states));
        }

        postMatrix(rowStack);
        postRightMenu();
    }

    private LinearLayout createRow(Visit[] sVisits, int i, LightExercisesInfo.TypeState[] states) {
        checkSize(sVisits);
        LinearLayout row = new LinearLayout(context);
        for (int j = 0; j < lightExercisesInfo.length; j++) {
            matrix[i][j] = new EvolutionCell(context, sVisits[j], states[lightExercisesInfo[j].getType()]);
            setListeners(matrix[i][j]);
            row.addView(matrix[i][j]);
        }
        return row;
    }

    private void checkSize(Visit[] sVisits) {
        try {
            if (sVisits.length != lightExercisesInfo.length) throw new NegativeArraySizeException();
        } catch (NegativeArraySizeException ex) {
            Logger.printError(ex, getClass());
        }
    }

    private void postMatrix(LinearLayout rowStack) {
        rowStack.addView(new HorizontalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        scroller.addView(rowStack);
        super.addView(scroller);
        super.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
    }

    private void postRightMenu() {
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

    public void setColumnColor(int columnIndex, int color) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[columnIndex][j].changeStatusForDelete(color);
        }
    }
}
