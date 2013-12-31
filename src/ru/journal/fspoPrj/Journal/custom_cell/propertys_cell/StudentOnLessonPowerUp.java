package ru.journal.fspoPrj.journal.custom_cell.propertys_cell;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

// высокая аткивность студента
public class StudentOnLessonPowerUp extends View {

    private static Bitmap greenTriangle;
    private static Bitmap redTriangle;

    public StudentOnLessonPowerUp(Context context, boolean status, int cellSize) {
        super(context);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setBackgroundDrawable(new VisualElement(status, cellSize));
    }

    private static class VisualElement extends Drawable {

        private boolean status;
        private static int offset;
        private static int triangleSize;

        public VisualElement(boolean status, int cellSize) {
            this.status = status;
            if (redTriangle == null) initCode(cellSize);
        }

        private void initCode(int cellSize) {
            VisualElement.triangleSize = cellSize / 3;
            VisualElement.offset = cellSize - triangleSize - GlobalConfig.ONE;

            greenTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);
            redTriangle = Bitmap.createBitmap(triangleSize, triangleSize, Bitmap.Config.ARGB_8888);

            generateTriangle(new Canvas(greenTriangle), Color.GREEN);
            generateTriangle(new Canvas(redTriangle), Color.RED);
        }

        private void generateTriangle(Canvas canvas, int color) {
            Paint setting = new Paint();
            setting.setColor(color);
            drawToCanvas(canvas, setting);
        }

        private void drawToCanvas(Canvas canvas, Paint setting) {
            for (int i = 0; i < triangleSize; i++)
                for (int j = i; j < triangleSize; j++)
                    canvas.drawPoint(j, i, setting);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap((status) ? greenTriangle : redTriangle, offset, 0, null);
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
