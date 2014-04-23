package ru.journal.fspoPrj.journal.edit_journal.elements.main_table;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.journal.looking_journal.elements.main_table.MScrollView;
import ru.journal.fspoPrj.journal.EvolutionCell;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class TeacherTableWithMarks extends LinearLayout {

    private Context context;
    private MScrollView scroller;
    private OnClickListener shortListener;
    private OnLongClickListener longClickListener;
    private ArrayList<Student> students;
    private ArrayList<LightExercisesInfo> lightExercisesInfo;
    private HashMap<Integer, ArrayList<Visit>> visits;
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

    public void createTable(LightVisits lightVisits, ArrayList<Student> students) {
        super.removeAllViews();
        scroller.removeAllViews();
        this.lightExercisesInfo = lightVisits.getExercisesInfo();
        this.visits = lightVisits.getStudentVisits();
        this.students = students;
        if (lightExercisesInfo.size() > 0) {
            matrix = new EvolutionCell[students.size()][lightExercisesInfo.size()];
            initMatrix();
        } else {
            matrix = new EvolutionCell[students.size()][1];
            initEmptyMatrix();
        }

    }

    private void initEmptyMatrix() {
        LinearLayout rowStack = new LinearLayout(context);
        rowStack.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < students.size(); i++) {
            rowStack.addView(createEmptyRow(i));
        }
        postMatrix(rowStack);
        postRightMenu();
    }

    private View createEmptyRow(int i) {
        LinearLayout row = new LinearLayout(context);
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[i][j] = new EvolutionCell(context);
            row.addView(matrix[i][j]);
        }
        return row;
    }

    private void initMatrix() {
        LinearLayout rowStack = new LinearLayout(context);
        rowStack.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < students.size(); i++) {
            rowStack.addView(createRow(visits.get(students.get(i).getIntegerID()), i, states));
        }

        postMatrix(rowStack);
        postRightMenu();
    }

    private LinearLayout createRow(ArrayList<Visit> sVisits, int i, LightExercisesInfo.TypeState[] states) {
        checkSize(sVisits);
        LinearLayout row = new LinearLayout(context);
        for (int j = 0; j < lightExercisesInfo.size(); j++) {
            matrix[i][j] = new EvolutionCell(context, sVisits.get(j), states[lightExercisesInfo.get(j).getType()]);
            setListeners(matrix[i][j]);
            row.addView(matrix[i][j]);
        }
        return row;
    }

    private void checkSize(ArrayList<Visit> sVisits) {
        try {
            if (sVisits.size() != lightExercisesInfo.size()) throw new NegativeArraySizeException();
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

    public void postRightMenu() {
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

    public void restoreState(LightVisits lightVisits, ArrayList<Student> students) {
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

    public void setState(EditJournalsCommunicator jC) {
        createTable(jC.getLightVisits(), jC.getStudents());
    }
}
