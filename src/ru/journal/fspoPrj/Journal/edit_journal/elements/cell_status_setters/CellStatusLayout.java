package ru.journal.fspoPrj.journal.edit_journal.elements.cell_status_setters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.EvolutionCell;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class CellStatusLayout extends ScrollView implements View.OnClickListener {

    // TODO refactoring

    public static final String EMPTY = "";
    public static final String POINTS = "Оценки";
    public static final String POINT_IMPORTANT = "Важность оценки";
    public static final String EXPERT_IMPORTANT_POINT_MARK = "Особая важность оценки";
    public static final String PERFORMANCE_STUDENT = "Активность студента";
    public static final String DELAY_STUDENT = "Опоздание студента";
    public static final String STUDENT_NEEDED_STATUS = "Необходимость присутсвия студента";

    private LinearLayout mainLay;
    private Context context;
    private EvolutionCell cell;

    public CellStatusLayout(Context context, EvolutionCell cell) {
        super(context);
        this.context = context;
        this.cell = cell;

        mainLay = new LinearLayout(context);
        mainLay.setOrientation(LinearLayout.VERTICAL);

        setPointsRow();
        setPointWeight();
        setMarkPoint();
        setStudentPerformance();
        setStudentDelay();
        setStudentNeedRow();
        super.addView(mainLay);
    }

    private void setPointsRow() {
        LinearLayout points = new LinearLayout(context);
        addTitle(POINTS);
        for (int i = 0; i <= 5; i++) {
            if (i == 1) continue;
            Point point = new Point(context, i);
            point.setOnClickListener(this);
            points.addView(point);
        }
        addElement(points);
    }

    private void setPointWeight() {
        LinearLayout paramsRow = new LinearLayout(context);
        addTitle(POINT_IMPORTANT);
        for (int i = 3; i >= 1; --i) {
            PointWeight pointWeight = new PointWeight(context, i);
            pointWeight.setOnClickListener(this);
            paramsRow.addView(pointWeight);
        }
        addElement(paramsRow);
    }

    private void setMarkPoint() {
        LinearLayout markRow = new LinearLayout(context);
        addTitle(EXPERT_IMPORTANT_POINT_MARK);
        for (int i = 0; i <= 1; i++) {
            MarkPoint markPoint = new MarkPoint(context, i);
            markPoint.setOnClickListener(this);
            markPoint.setTextSize(Config.getTeacherJournalDialogButtonsTextSize());
            markRow.addView(markPoint);
        }
        addElement(markRow);
    }

    private void setStudentPerformance() {
        LinearLayout performance = new LinearLayout(context);
        addTitle(PERFORMANCE_STUDENT);
        for (int i = 1; i <= 2; i++) {
            PerformanceStudent performanceStudent = new PerformanceStudent(context, i);
            performanceStudent.setOnClickListener(this);
            performanceStudent.setTextSize(Config.getTeacherJournalDialogButtonsTextSize());
            performance.addView(performanceStudent);
        }
        addElement(performance);
    }

    private void setStudentDelay() {
        LinearLayout slowL = new LinearLayout(context);
        addTitle(DELAY_STUDENT);
        for (int i = 1; i <= 3; i++) {
            StudentDelay studentDelay = new StudentDelay(context, i);
            studentDelay.setOnClickListener(this);
            slowL.addView(studentDelay);
        }
        addElement(slowL);
    }

    private void setStudentNeedRow() {
        LinearLayout neededStudent = new LinearLayout(context);
        addTitle(STUDENT_NEEDED_STATUS);
        for (int i = 1; i <= 3; i++) {
            Needed needed = new Needed(context, i);
            needed.setOnClickListener(this);
            neededStudent.addView(needed);
        }
        addElement(neededStudent);
    }

    private void addElement(LinearLayout element) {
        setSeparateLine();
        mainLay.addView(element);
        setSeparateLine();
    }

    private void setSeparateLine() {
        mainLay.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getFunctionDialogTransparentSeparateLineHeight()));
    }

    private void addTitle(String text) {
        mainLay.addView(new SerifTextView(context, Gravity.LEFT, text));
        mainLay.addView(new HorizontalLine(context, Color.BLACK));
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Point) {
            handlePointClick((Point) view);
        } else if (view instanceof Needed) {
            handleNeededStatus((Needed) view);
        } else if (view instanceof StudentDelay) {
            handleStudentSlow((StudentDelay) view);
        } else if (view instanceof PointWeight) {
            handlePointWeight((PointWeight) view);
        } else if (view instanceof MarkPoint) {
            handlePointMark((MarkPoint) view);
        } else if (view instanceof PerformanceStudent) {
            handleStudentPerformance((PerformanceStudent) view);
        }
    }

    private void handleStudentPerformance(PerformanceStudent performanceStudent) {
        cell.changeStudentPerformance(performanceStudent.getIndex());
    }

    private void handlePointMark(MarkPoint markPoint) {
        cell.changePointMark(markPoint.getIndex());
    }

    private void handlePointWeight(PointWeight pointWeight) {
        cell.changePointWeight(pointWeight.getIndex());
    }

    private void handleStudentSlow(StudentDelay studentDelay) {
        cell.changeStudentSlowStatus(studentDelay.getIndex());
    }

    private void handleNeededStatus(Needed needed) {
        cell.changeNeededStatus(needed.getIndex());
    }

    private void handlePointClick(Point point) {
        cell.changePoint(point.getIndex());
    }

    private class Point extends Button {

        public static final String REMOVE = "X";

        public final int point;

        public Point(Context context, int point) {
            super(context);
            this.point = point;
            if (point == 0) {
                setText(REMOVE);
            } else {
                setText(String.valueOf(point));
            }
        }

        public int getIndex() {
            return point;
        }
    }

    private class Needed extends Button {

        public static final String LOW = "Низкая";
        public static final String MIDDLE = "Средняя";
        public static final String UPPER = "Высокая";
        private final int index;

        private Needed(Context context, int index) {
            super(context);
            this.index = index;
            setText(calculateText());
            setTextSize(Config.getTeacherJournalDialogButtonsTextSize());
        }

        private String calculateText() {
            switch (index) {
                case 1:
                    return LOW;
                case 2:
                    return MIDDLE;
                case 3:
                    return UPPER;
                default:
                    return EMPTY;
            }
        }

        public int getIndex() {
            return index;
        }
    }

    private class StudentDelay extends Button {

        public static final String SLOW = "Опоздал";
        public static final String FAST = "Ранний уход";
        public static final String OLL_OF_THEM = "Опоздал и ушол";
        private final int index;

        public StudentDelay(Context context, int index) {
            super(context);
            this.index = index;
            setText(calculateText());
            setTextSize(Config.getTeacherJournalDialogButtonsTextSize());
        }

        private String calculateText() {
            switch (index) {
                case 1:
                    return SLOW;
                case 2:
                    return FAST;
                case 3:
                    return OLL_OF_THEM;
                default:
                    return EMPTY;
            }
        }

        public int getIndex() {
            return index;
        }
    }

    private class PointWeight extends Button {

        public static final String LOW = "Низкая";
        public static final String MIDDLE = "Средняя";
        public static final String UPPER = "Высокая";
        private final int index;

        private PointWeight(Context context, int index) {
            super(context);
            this.index = index;
            setText(calculateText());
            setTextSize(Config.getTeacherJournalDialogButtonsTextSize());
        }

        private String calculateText() {
            switch (index) {
                case 3:
                    return LOW;
                case 2:
                    return MIDDLE;
                case 1:
                    return UPPER;
                default:
                    return EMPTY;
            }
        }

        public int getIndex() {
            return index;
        }
    }

    private class MarkPoint extends Button {

        public static final String REMOVE = "X";
        public static final String IMPORTANT = "Важно !";

        public final int index;

        public MarkPoint(Context context, int index) {
            super(context);
            this.index = index;
            if (index == 0) {
                setText(REMOVE);
            } else {
                setText(IMPORTANT);
            }
        }

        public int getIndex() {
            return index;
        }
    }

    private class PerformanceStudent extends Button {

        public static final String LOW = "Низкая";
        public static final String UP = "Высокая";

        public final int point;

        public PerformanceStudent(Context context, int point) {
            super(context);
            this.point = point;
            if (point == 1) {
                setText(LOW);
            } else {
                setText(UP);
            }
        }

        public int getIndex() {
            return point;
        }
    }
}
