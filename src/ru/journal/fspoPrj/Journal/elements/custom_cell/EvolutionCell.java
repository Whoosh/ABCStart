package ru.journal.fspoPrj.journal.elements.custom_cell;

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

    public EvolutionCell(Context context, Visit visit, LightExercisesInfo.TypeState state) {
        super(context);
        this.stateKey = generateKey(visit, state);
        setLayoutParams(VIEW_PARAMS);
        setState(stateKey, visit, state);
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
        addBackground(canvas, stateBgColor);
        handleVisitNeeded(canvas, visit.getVisitNeed());
        handleVisitPoint(canvas, visit.getPoint(), visit.getMarkNeed(), visit.getDelay());
        handleVisitPresence(canvas, visit.getPresence());
        handleVisitPerformance(canvas, visit.getPerformance());
        handleVisitWeightRank(canvas, visit.getWeight());
        return element;
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
        if (Visit.PerformanceState.BAD.ordinal() == performance) {
            addStudentPowerUPStatus(canvas, Color.RED);
        } else if (Visit.PerformanceState.GOOD.ordinal() == performance) {
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

    private void handleVisitPoint(Canvas canvas, int point, int marked, int delay) {
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

        if (Visit.PointState.FAIL.ordinal() == point) {
            // TODO
        } else if (Visit.PointState.TRUE.ordinal() == point) {
            // TODO
        } else {
            addStudentRank(canvas, pointColor, rank);
        }
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
        return String.valueOf(state.getColor()) + Arrays.toString(visit.getAllStates());
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
