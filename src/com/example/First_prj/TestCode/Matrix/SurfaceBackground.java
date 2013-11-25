package com.example.First_prj.TestCode.Matrix;

import android.content.Context;
import android.graphics.*;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;
// TODO идея ок, как отобразить, подумать, если выгорит зарефакторить
public class SurfaceBackground extends SurfaceView implements Runnable {
    private Context context;
    private Thread thread = null;
    private SurfaceHolder holder;
    private boolean isWorkAccepted = false;
    private Canvas canvas;
    private WindowManager windowManager;
    private Display display;
    private int indexer_X = 15;
    private int indexer_Y = 15;
    private Bitmap matrixMap;
    private boolean starterFlag;
    private Symbols symbols;
    private int len;

    public SurfaceBackground(Context context) {
        super(context);
        this.context = context;
        holder = getHolder();
        starterFlag = false;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        matrixMap = Bitmap.createBitmap(20, 22, Bitmap.Config.RGB_565);
        for (int i = 0; i < 22; i++)
            for (int j = 0; j < 20; j++)
                matrixMap.setPixel(j, i, Color.BLACK);
        symbols = new Symbols(10);

    }

    @Override
    public void run() {
        canvas = null;
        while (isWorkAccepted) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (holder.getSurface().isValid()) {
                pushGraphicToCanvas();
            }
        }
    }

    private void pushGraphicToCanvas() {
        int textSize = 20;
        Paint paint = new Paint();
        paint.setTextSize(textSize);

        int indexes_Y[] = new int[display.getHeight() / (textSize + 2)];
        int indexes_X[] = new int[display.getWidth() / (textSize + 2)];
        indexes_Y[0] = 0;
        indexes_X[0] = 0;
        for (int i = 1; i < indexes_Y.length; i++)
            indexes_Y[i] = (indexes_Y[i - 1] += textSize + 2);
        for (int i = 1; i < indexes_X.length; i++)
            indexes_X[i] = (indexes_X[i - 1] += textSize + 2);
        indexer_Y = indexes_Y[new Random().nextInt(indexes_Y.length)];
        indexer_X = indexes_X[new Random().nextInt(indexes_X.length)];
        len = symbols.getStringLen();
        for (int i = 0, i_Y = indexer_Y, green = 0; i < len; i++, i_Y += textSize + 2) {
            paint.setColor(Color.rgb(0, green += (255 / len), 0));

            try {
                canvas = holder.lockCanvas();
                canvas.drawBitmap(matrixMap, indexer_X, i_Y - textSize + 2, null);
                holder.unlockCanvasAndPost(canvas);

                canvas = holder.lockCanvas();
                canvas.drawBitmap(matrixMap, indexer_X, i_Y - textSize + 2, null);
                holder.unlockCanvasAndPost(canvas);

                canvas = holder.lockCanvas();
                canvas.drawText(symbols.getString(), indexer_X, i_Y, paint);
                holder.unlockCanvasAndPost(canvas);

                canvas = holder.lockCanvas();
                canvas.drawText(symbols.getString2(), indexer_X, i_Y, paint);
                holder.unlockCanvasAndPost(canvas);
            } catch (NullPointerException e) {
            }
        }

    }

    public void stopThread() {
        if (thread != null)
            while (true) {
                try {
                    thread.join();
                    break;
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        isWorkAccepted = false;
        thread = null;
    }

    public void resume() {
        isWorkAccepted = true;
        thread = new Thread(this);
        thread.start();
    }

    public void kill() {
        if (thread != null && thread.isAlive())
            try {
                thread.stop();
            } catch (Exception ex) {
                System.out.println(ex);
            }
    }
}

