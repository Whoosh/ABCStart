package ru.journal.fspoPrj.journal.elements.custom_cell.propertys_cell;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;

public class StudentOnLesson extends View {

    private static Bitmap greenTriangle;
    private static Bitmap redTriangle;

    public StudentOnLesson(Context context, boolean status, int cellSize) {
        super(context);
        super.setBackgroundDrawable(new VisualElement(status, cellSize));
    }

    private static class VisualElement extends Drawable {

        private boolean status;

        public VisualElement(boolean status, int cellSize) {
            this.status = status;
            if (greenTriangle == null) initCode(cellSize);
        }

        private void initCode(int cellSize) {
            final int triangleSize = cellSize / 3;

            greenTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);
            redTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);

            generateTriangle(new Canvas(greenTriangle), Color.GREEN, triangleSize);
            generateTriangle(new Canvas(redTriangle), Color.RED, triangleSize);
        }

        private void generateTriangle(Canvas canvas, int color, int triangleSize) {
            Paint setting = new Paint();
            setting.setColor(color);
            drawOnCanvas(canvas, setting,triangleSize);
        }

        private void drawOnCanvas(Canvas canvas, Paint setting,int triangleSize) {
            for (int i = 0; i < triangleSize; i++)
                for (int j = 0; j < triangleSize - i; j++)
                    canvas.drawPoint(j, i, setting);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap((status) ? greenTriangle : redTriangle, 0, 0, null);
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

