package ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.propertys_cell;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;

public class StudentOnLessonNeeded extends View {

    private static Bitmap greenTriangle;
    private static Bitmap redTriangle;
    private static Bitmap yellowTriangle;

    public StudentOnLessonNeeded(Context context, boolean status, int cellSize) {
        super(context);
        super.setBackgroundDrawable(new VisualElement(status, cellSize));
    }

    public StudentOnLessonNeeded(Context context, int cellSize) {
        super(context);
        super.setBackgroundDrawable(new VisualElement(cellSize));
    }

    private static class VisualElement extends Drawable {

        private boolean greenStatus;
        private boolean yellowStatus;
        private boolean redStatus;

        private static int offset;

        public VisualElement(boolean status, int cellSize) {
            greenStatus = status;
            redStatus = !greenStatus;
            if (redTriangle == null) initCode(cellSize);
        }

        public VisualElement(int cellSize) {
            yellowStatus = true;
            if (redTriangle == null) initCode(cellSize);
        }

        private void initCode(int cellSize) {
            final int triangleSize = cellSize / 3;
            offset = cellSize - triangleSize - 1;

            greenTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);
            redTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);
            yellowTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);

            generateTriangle(new Canvas(greenTriangle), Color.GREEN, triangleSize);
            generateTriangle(new Canvas(redTriangle), Color.RED, triangleSize);
            generateTriangle(new Canvas(yellowTriangle), Color.YELLOW, triangleSize);
        }

        private void generateTriangle(Canvas canvas, int color, int triangleSize) {
            Paint setting = new Paint();
            setting.setColor(color);
            drawToCanvas(canvas, setting, triangleSize);
        }

        private void drawToCanvas(Canvas canvas, Paint setting, int triangleSize) {
            for (int i = 0; i < triangleSize; i++)
                for (int j = 0; j < i; j++)
                    canvas.drawPoint(j, i, setting);
        }

        @Override
        public void draw(Canvas canvas) {
            if (yellowStatus) canvas.drawBitmap(yellowTriangle, 0, offset, null);
            if (greenStatus) canvas.drawBitmap(greenTriangle, 0, offset, null);
            if (redStatus) canvas.drawBitmap(redTriangle, 0, offset, null);
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
