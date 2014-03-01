package ru.journal.fspoPrj.journal.looking_journal.elements.student_list;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.journal.fspoPrj.journal.config.Config;

public class StudentElement extends TextView {

    public StudentElement(Context context, String student) {
        super(context);
        setText(student);
        setTextSize(Config.getStudentElementTextSize());
        setGravity(Gravity.CENTER_VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(Config.getStudentElementWidth(), Config.getStudentElementHeight()));
        setBackgroundDrawable(new EndLine(Config.getStudentElementWidth(), Config.getStudentElementHeight()));
    }

    public static class EndLine extends Drawable {

        public static final int LINE_WIDTH = Config.getJournalEndLineWidth();
        private static Bitmap line;

        public EndLine(int studentElementWidth, int studentElementHeight) {
            if (line == null) {
                line = Bitmap.createBitmap(studentElementWidth, studentElementHeight, Bitmap.Config.ARGB_8888);
                Canvas drawer = new Canvas(line);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                for (int i = 0; i < studentElementHeight; i++) {
                    for (int j = studentElementWidth - LINE_WIDTH; j < studentElementWidth; j++) {
                        drawer.drawPoint(j, i, paint);
                    }
                }
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.LTGRAY);
            canvas.drawBitmap(line, 0, 0, null);
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
