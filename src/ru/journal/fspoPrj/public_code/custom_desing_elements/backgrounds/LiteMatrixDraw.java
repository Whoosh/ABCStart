package ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

import java.util.Random;

public class LiteMatrixDraw extends Drawable {

    private int maxLen;
    private Paint paint;
    private int indexes_Y[];
    private int indexes_X[];
    private Bitmap background;
    private final static char[] zeroOne = {'0', '1'};

    private static final byte TEXT_SIZE = 12;

    // j4fun TODO if actual
    public LiteMatrixDraw(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int display_H = windowManager.getDefaultDisplay().getHeight();
        int display_W = windowManager.getDefaultDisplay().getWidth();

        background = Bitmap.createBitmap(display_W, display_H, Bitmap.Config.RGB_565);
        paint = new Paint();
        paint.setTextSize(TEXT_SIZE);

        maxLen = (display_H / (TEXT_SIZE + 2)); // максимально возможная длинна полоски символов
        int density = (maxLen * (display_W / (TEXT_SIZE + 2))) / (TEXT_SIZE / 2);

        indexes_Y = new int[maxLen]; // набор возможных координат по знакоместам.
        indexes_X = new int[display_W / (TEXT_SIZE + 2)]; // аналогично

        // заполнение координат, что-бы не рандомить их в цикле с прорисовкой.
        for (int i = GlobalConfig.ONE; i < indexes_Y.length; i++)
            indexes_Y[i] = (indexes_Y[i - GlobalConfig.ONE] += TEXT_SIZE + 2);
        for (int i = GlobalConfig.ONE; i < indexes_X.length; i++)
            indexes_X[i] = (indexes_X[i - GlobalConfig.ONE] += TEXT_SIZE + 2);

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
        final Random random = new Random();

        int indexer_Y = indexes_Y[random.nextInt(indexes_Y.length)];
        int indexer_X = indexes_X[random.nextInt(indexes_X.length)];
        int textLineLen = random.nextInt(maxLen) + 3;
        int colorFragment = (255 / textLineLen);

        final Bitmap eraser = Bitmap.createBitmap(TEXT_SIZE, TEXT_SIZE + GlobalConfig.ONE, Bitmap.Config.RGB_565);

        for (int i = 0, i_Y = indexer_Y, rgb = 0; i < textLineLen; i++, i_Y += TEXT_SIZE + 2) {
            paint.setColor(Color.rgb(0, rgb += colorFragment, 0));
            canvas.drawBitmap(eraser, indexer_X, i_Y - TEXT_SIZE + GlobalConfig.ONE, null);
            canvas.drawText(zeroOne, random.nextInt(zeroOne.length), GlobalConfig.ONE, indexer_X, i_Y, paint);
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
