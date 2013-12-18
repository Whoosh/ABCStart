package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.example.First_prj.R;

public class LeftToRightHorizontalBoldGradientLine extends View {

    private static final byte DEFAULT_HEIGHT = 4;

    public LeftToRightHorizontalBoldGradientLine(Context context, int height) {
        super(context);
        initCode(context, height);
    }

    private void initCode(Context context, int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height));
        super.setBackgroundDrawable(new Gradient(context, height));
    }

    public LeftToRightHorizontalBoldGradientLine(Context context) {
        super(context);
        initCode(context, DEFAULT_HEIGHT);
    }

    private class Gradient extends Drawable {

        private Bitmap gradient;
        private WindowManager windowManager;
        private int windowWidth;
        private int height;

        public Gradient(Context context, int height) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowWidth = windowManager.getDefaultDisplay().getWidth();
            this.height = height;

            gradient = Bitmap.createBitmap(windowWidth, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(gradient);
            canvas.drawColor(Color.TRANSPARENT);
            addLine(canvas);
        }

        private void addLine(Canvas canvas) {
            Paint settings = new Paint();
            float alpha = 255;
            final int R = 0, G = 100, B = 150;
            float fallPoint = alpha / windowWidth;
            for (int i = 0; i < windowWidth; ++i, alpha -= fallPoint) {
                for (int j = 0; j < height; j++) {
                    settings.setColor(Color.argb((int) alpha, R, G, B));
                    canvas.drawPoint(i, j, settings);
                }
            }
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(gradient, 0, 0, null);
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
