package com.example.First_prj.ForAllCode.DesigneElements.Backgrounds;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class WhiteGradient extends Drawable {

    public WhiteGradient(){

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
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
