package ru.journal.fspoPrj.search_users.search_all.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

public class SearchUsersDivider extends Drawable {

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
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
