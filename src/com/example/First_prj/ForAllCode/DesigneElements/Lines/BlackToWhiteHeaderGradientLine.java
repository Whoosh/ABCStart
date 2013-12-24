package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;


public class BlackToWhiteHeaderGradientLine extends View {
//
    private final static byte DEFAULT_HEIGHT = 3;

    public BlackToWhiteHeaderGradientLine(Context context, int height) {
        super(context);
        initCode(context, height);
    }

    private void initCode(Context context, int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, height));
        super.setBackgroundDrawable(new Gradient(context, height));
    }

    public BlackToWhiteHeaderGradientLine(Context context) {
        super(context);
        initCode(context, (int) (GlobalConfig.getPixelDensity() * DEFAULT_HEIGHT));
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
            final float starter = 255;
            float stepSum = 0, fallPoint = starter / windowWidth;
            for (int i = 0; i < windowWidth; ++i, stepSum += fallPoint) {
                for (int j = 0; j < height; j++) {
                    settings.setColor(Color.rgb((int) stepSum, (int) stepSum, (int) stepSum));
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
