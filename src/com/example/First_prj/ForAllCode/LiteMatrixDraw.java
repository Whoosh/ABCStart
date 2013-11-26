package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import com.example.First_prj.Constants;

import java.util.Random;

public class LiteMatrixDraw extends Drawable {
    private int display_H;
    private int display_W;
    private Bitmap eraser;
    private final byte textSize = 12;
    private Paint paint;
    private int indexer_Y;
    private int indexer_X;
    private int indexes_Y[];
    private int indexes_X[];
    private Random random;
    private char[] zeroOne = {'0', '1'};
    private int density;
    private int maxLen;
    private int textLineLen;
    private int colorFragment;
    private Bitmap background;

    public LiteMatrixDraw(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display_H = windowManager.getDefaultDisplay().getHeight();
        display_W = windowManager.getDefaultDisplay().getWidth();
        eraser = Bitmap.createBitmap(textSize, textSize + Constants.ONE, Bitmap.Config.RGB_565); // по дефолту чёрный цвет. Резинка.
        background = Bitmap.createBitmap(display_W, display_H, Bitmap.Config.RGB_565);
        paint = new Paint();
        paint.setTextSize(textSize);

        random = new Random();

        maxLen = (display_H / (textSize + 2)); // максимально возможная длинна полоски символов
        density = (maxLen * (display_W / (textSize + 2))) / (textSize / 2); // плотность разброса

        indexes_Y = new int[maxLen]; // набор возможных координат по знакоместам.
        indexes_X = new int[display_W / (textSize + 2)]; // аналогично

        // заполнение координат, что-бы не рандомить их в цикле с прорисовкой.
        for (int i = Constants.ONE; i < indexes_Y.length; i++)
            indexes_Y[i] = (indexes_Y[i - Constants.ONE] += textSize + 2);
        for (int i = Constants.ONE; i < indexes_X.length; i++)
            indexes_X[i] = (indexes_X[i - Constants.ONE] += textSize + 2);

        Canvas canvasBuffer = new Canvas(background);
        canvasBuffer.drawColor(Color.BLACK);
        for (int i = 0; i < density; i++)
            pushGraphicToCanvas(canvasBuffer);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
    }

    private void pushGraphicToCanvas(Canvas canvas) {
        indexer_Y = indexes_Y[random.nextInt(indexes_Y.length)];
        indexer_X = indexes_X[random.nextInt(indexes_X.length)];
        textLineLen = random.nextInt(maxLen) + 3; // минимальный размер
        colorFragment = (255 / textLineLen);
        for (int i = 0, i_Y = indexer_Y, rgb = 0; i < textLineLen; i++, i_Y += textSize + 2) {
            paint.setColor(Color.rgb(0, rgb += colorFragment, 0));
            canvas.drawBitmap(eraser, indexer_X, i_Y - textSize + Constants.ONE, null); // не совсем экономично...
            canvas.drawText(zeroOne, random.nextInt(2), Constants.ONE, indexer_X, i_Y, paint);
        }
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
