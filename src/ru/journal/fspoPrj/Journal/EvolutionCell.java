package ru.journal.fspoPrj.journal;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;

import java.util.Arrays;
import java.util.HashMap;


public class EvolutionCell extends View {

    private static final char ATTENTION = '!';
    private static final char PLUS = '+';
    private static final char NOTHING = 'н';
    private static final char EMPTY_STUDENT_CELL_MARK = 'x';

    private static final int OFFSET;
    private static final int TRIANGLE_SIZE;
    private static final int CELL_SIZE;
    private static final int TEXT_SIZE;
    private static final int TEXT_OFFSET = 10;
    private static final int TEXT_CENTERED_X;
    private static final int TEXT_CENTERED_Y;
    private static final ViewGroup.LayoutParams VIEW_PARAMS;

    private static HashMap<String, Bitmap> statesBuffer;
    private static Paint drawingElementSettings;

    private String stateKey;
    private Visit visit;
    private LightExercisesInfo.TypeState state;

    public static final int MARGIN_SEPARATOR = 1;

    static {
        CELL_SIZE = Config.getEvolutionCellSize() + MARGIN_SEPARATOR;
        TEXT_SIZE = Config.getEvolutionTextSize();
        statesBuffer = new HashMap<>();
        drawingElementSettings = new Paint();
        drawingElementSettings.setTextSize(TEXT_SIZE);

        VIEW_PARAMS = new ViewGroup.LayoutParams(CELL_SIZE, CELL_SIZE);

        TRIANGLE_SIZE = CELL_SIZE / 4;
        OFFSET = VIEW_PARAMS.height - TRIANGLE_SIZE;

        TEXT_CENTERED_X = (int) ((CELL_SIZE / 2) +
                ((drawingElementSettings.descent() + drawingElementSettings.ascent()) / 2) + TEXT_SIZE / TEXT_OFFSET);
        TEXT_CENTERED_Y = (int) ((CELL_SIZE / 2) -
                ((drawingElementSettings.descent() + drawingElementSettings.ascent()) / 2));

    }

    public EvolutionCell(Context context) {
        super(context);
        setLayoutParams(VIEW_PARAMS);
        setState(generateKey(new Visit(), LightExercisesInfo.TypeState.NOT_SET), new Visit(), LightExercisesInfo.TypeState.NOT_SET);
    }

    public EvolutionCell(Context context, Visit visit, LightExercisesInfo.TypeState state) {
        super(context);
        this.visit = visit;
        this.state = state;
        this.stateKey = generateKey(visit, state);
        setLayoutParams(VIEW_PARAMS);
        setState(stateKey, visit, state);
    }

    public Visit getVisit() {
        return visit;
    }

    public String getStringVisitID() {
        return String.valueOf(visit.getVisitID());
    }

    public int getComingCellStatus() {
        return visit.getPresence();
    }

    public void changeComingStatus() {
        if (visit.getPresence() == Visit.PresentsState.DRINK_VODKA_WITH_BEAR.ordinal()) {
            visit.setPresence(Visit.PresentsState.COMING_ON_LESSON);
        } else {
            visit.setPresence(Visit.PresentsState.DRINK_VODKA_WITH_BEAR);
        }
        acceptChange();
    }

    public void changePoint(int point) {
        visit.setPoint(Visit.PointState.values()[point]);
        acceptChange();
    }

    public void changeNeededStatus(int index) {
        visit.setVisitNeed(Visit.VisitNeedState.values()[index]);
        acceptChange();
    }

    public void changeStudentSlowStatus(int index) {
        visit.setDelay(Visit.DelayState.values()[index]);
        acceptChange();
    }

    public void changePointWeight(int index) {
        visit.setWeight(Visit.WeightState.values()[index]);
        acceptChange();
    }

    public void changePointMark(int index) {
        visit.setMarkNeed(Visit.MarkNeedState.values()[index]);
        acceptChange();
    }

    public void changeStudentPerformance(int index) {
        visit.setPerformance(Visit.PerformanceState.values()[index]);
        acceptChange();
    }

    private void setState(String elementKey, Visit visit, LightExercisesInfo.TypeState state) {
        if (statesBuffer.containsKey(elementKey)) {
            setBackgroundDrawable(new Setter(statesBuffer.get(elementKey)));
        } else {
            Bitmap bitmapState = createNewState(visit, state.getColor());
            statesBuffer.put(elementKey, bitmapState);
            setBackgroundDrawable(new Setter(bitmapState));
        }
    }

    private Bitmap createNewState(Visit visit, int stateBgColor) {
        Bitmap element = Bitmap.createBitmap(VIEW_PARAMS.width, VIEW_PARAMS.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(element);
        if (visit.isEmptyCell()) {
            addBackground(canvas, Color.WHITE);
            handleEmptyStudentCell(canvas);
            return element;
        }
        addBackground(canvas, stateBgColor);
        handleVisitNeeded(canvas, visit.getVisitNeed());
        handleVisitPoint(canvas, visit.getPoint(), visit.getMarkNeed(), visit.getDelay(), visit.getPresence());
        handleVisitPresence(canvas, visit.getPresence());
        handleVisitPerformance(canvas, visit.getPerformance());
        handleVisitWeightRank(canvas, visit.getWeight());
        return element;
    }

    private void handleEmptyStudentCell(Canvas canvas) {
        addStudentRank(canvas, Color.GRAY, EMPTY_STUDENT_CELL_MARK);
    }

    private void handleVisitWeightRank(Canvas canvas, int weight) {
        if (Visit.WeightState.LIGHT.ordinal() == weight) {
            addWeightOfRank(canvas, Color.GREEN);
        } else if (Visit.WeightState.MIDDLE.ordinal() == weight) {
            addWeightOfRank(canvas, Color.YELLOW);
        } else if (Visit.WeightState.OVER9000KG.ordinal() == weight) {
            addWeightOfRank(canvas, Color.RED);
        } else {
            // nothing
        }
    }

    private void handleVisitPerformance(Canvas canvas, int performance) {
        if (Visit.PerformanceState.HIGH.ordinal() == performance) {
            addStudentPowerUPStatus(canvas, Color.RED);
        } else if (Visit.PerformanceState.LOW.ordinal() == performance) {
            addStudentPowerUPStatus(canvas, Color.GREEN);
        } else {
            // nothing
        }
    }

    private void handleVisitPresence(Canvas canvas, int presence) {
        if (Visit.PresentsState.DRINK_VODKA_WITH_BEAR.ordinal() == presence) {
            addStudentComingOnLesson(canvas, Color.RED);
        } else {
            addStudentComingOnLesson(canvas, Color.GREEN);
        }
    }

    private void handleVisitPoint(Canvas canvas, int point, int marked, int delay, int presence) {
        char[] rank;
        int pointColor;

        if (Visit.DelayState.SLOW.ordinal() == delay) {
            pointColor = Color.BLUE;
        } else if (Visit.DelayState.FAST.ordinal() == delay) {
            pointColor = Color.YELLOW;
        } else if (Visit.DelayState.BATMAN.ordinal() == delay) {
            pointColor = Color.RED;
        } else {
            pointColor = Color.BLACK;
        }

        if (Visit.MarkNeedState.MARKED.ordinal() == marked) {
            rank = new char[]{String.valueOf(point).charAt(0), ATTENTION};
        } else {
            rank = new char[]{String.valueOf(point).charAt(0)};
        }

        if (Visit.PointState.FAIL.ordinal() == point || Visit.PointState.TRUE.ordinal() == point) {
            if (Visit.PresentsState.DRINK_VODKA_WITH_BEAR.ordinal() == presence) {
                rank[0] = NOTHING;
            } else {
                rank[0] = PLUS;
            }
        }

        addStudentRank(canvas, pointColor, rank);
    }

    private void handleVisitNeeded(Canvas canvas, int state) {
        if (Visit.VisitNeedState.HIGH.ordinal() == state) {
            addStudentNeededStatus(canvas, Color.RED);
        } else if (Visit.VisitNeedState.MIDDLE.ordinal() == state) {
            addStudentNeededStatus(canvas, Color.YELLOW);
        } else if (Visit.VisitNeedState.LOW.ordinal() == state) {
            addStudentNeededStatus(canvas, Color.GREEN);
        } else {
            // nothing
        }
    }

    private void addStudentNeededStatus(Canvas canvas, int color) {
        drawingElementSettings.setColor(color);
        for (int i = 0; i < TRIANGLE_SIZE; i++) {
            for (int j = 0; j < i; j++) {
                canvas.drawPoint(j + MARGIN_SEPARATOR, OFFSET + i, drawingElementSettings);
            }
        }
    }

    private void addStudentPowerUPStatus(Canvas canvas, int color) {
        drawingElementSettings.setColor(color);
        for (int i = 0; i < TRIANGLE_SIZE; i++) {
            for (int j = i; j < TRIANGLE_SIZE; j++) {
                canvas.drawPoint(OFFSET + j, i + MARGIN_SEPARATOR, drawingElementSettings);
            }
        }
    }

    private void addWeightOfRank(Canvas canvas, int color) {
        drawingElementSettings.setColor(color);
        for (int i = 0; i < TRIANGLE_SIZE; i++) {
            for (int j = TRIANGLE_SIZE - i; j < TRIANGLE_SIZE; j++) {
                canvas.drawPoint(OFFSET + j, OFFSET + i, drawingElementSettings);
            }
        }
    }

    private void addStudentComingOnLesson(Canvas canvas, int color) {
        drawingElementSettings.setColor(color);
        for (int i = 0; i < TRIANGLE_SIZE; i++) {
            for (int j = 0; j < TRIANGLE_SIZE - i; j++) {
                canvas.drawPoint(j + MARGIN_SEPARATOR, i + MARGIN_SEPARATOR, drawingElementSettings);
            }
        }
    }

    private void addStudentRank(Canvas canvas, int color, char... rank) {
        drawingElementSettings.setColor(color);
        canvas.drawText(rank, 0, rank.length, TEXT_CENTERED_X, TEXT_CENTERED_Y, drawingElementSettings);
    }

    private void addBackground(Canvas element, int color) {
        drawingElementSettings.setColor(color);
        for (int i = MARGIN_SEPARATOR; i < CELL_SIZE; i++) {
            for (int j = MARGIN_SEPARATOR; j < CELL_SIZE; j++) {
                element.drawPoint(j, i, drawingElementSettings);
            }
        }
    }

    private static String generateKey(Visit visit, LightExercisesInfo.TypeState state) {
        return String.valueOf(state.getEnID()) + Arrays.toString(visit.getAllStates());
    }

    private void acceptChange() {
        this.stateKey = generateKey(visit, state);
        setState(stateKey, visit, state);
    }

    public void changeStatusForDelete(int color) {
        // TODO
    }

    public boolean isEmpty() {
        return visit.isEmptyCell();
    }

    public boolean hasPoint() {
        return visit.getPoint() != 0 && visit.getPoint() != 1;
    }


    private static class Setter extends Drawable {

        private Bitmap cellState;

        private Setter(Bitmap cellState) {
            this.cellState = cellState;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(cellState, 0, 0, null);
        }

        @Override
        public void setAlpha(int i) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
