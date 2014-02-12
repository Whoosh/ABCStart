package ru.journal.fspoPrj.journal.elements.custom_cell;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import ru.journal.fspoPrj.journal.config.Config;

import java.util.HashMap;


public class EvolutionCell extends View {

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

    private String jsonKey; // TODO обязательно сделать Integer[] как будут извесны данные. если это возможно.

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

    public EvolutionCell(Context context, String stateJson) {
        super(context);
        this.jsonKey = stateJson;
        setLayoutParams(VIEW_PARAMS);
        setState(stateJson);
    }

    public void setStudentComingOnLesson() {
        // TODO EDIT JSON;
        setState(this.jsonKey);
        // Для каждой клетки, имзеняется ключь в зависимости от ф-и. по ключу идём за битмапом.
    }

    private void setState(String stateJson) {
        if (statesBuffer.containsKey(stateJson)) {
            setBackgroundDrawable(new Setter(statesBuffer.get(stateJson)));
        } else {
            Bitmap bitmapState = createNewState(stateJson);
            statesBuffer.put(stateJson, bitmapState);
            setBackgroundDrawable(new Setter(bitmapState));
        }
    }

    private Bitmap createNewState(String jsonState) {
        Bitmap element = Bitmap.createBitmap(VIEW_PARAMS.width, VIEW_PARAMS.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(element);
        // TODO PARSE JSON
        addBackground(canvas, Color.WHITE);
        addStudentNeededStatus(canvas, Color.RED);
        //addStudentPowerUPStatus(canvas, Color.BLUE);
        //addWeightOfRank(canvas, Color.CYAN);
        addStudentComingOnLesson(canvas, Color.MAGENTA);
        addStudentRank(canvas, Color.BLACK, '+');

        return element;
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
