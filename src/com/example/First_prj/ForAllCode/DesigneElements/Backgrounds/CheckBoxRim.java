package com.example.First_prj.ForAllCode.DesigneElements.Backgrounds;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import com.example.First_prj.ForAllCode.GlobalConfig;

import static com.example.First_prj.ForAllCode.GlobalConfig.MainSettingsConfig.*;

public class CheckBoxRim extends Drawable {
//
    private Bitmap box;

    public CheckBoxRim() {
        box = Bitmap.createBitmap(getCheckBoxSize(), getCheckBoxSize(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(box);
        setBoxGround(canvas);
    }

    private void setBoxGround(Canvas canvas) {
        Paint setting = new Paint();
        setting.setColor(getCheckBoxColor());
        canvas.drawColor(getFormBackgroundColor());

        final byte VOID_LEN = 10;
        for (int i = VOID_LEN - GlobalConfig.ONE; i < getCheckBoxSize() - VOID_LEN; i++) {
            canvas.drawPoint(i, VOID_LEN - GlobalConfig.ONE, setting);
            canvas.drawPoint(VOID_LEN - GlobalConfig.ONE, i, setting);
            canvas.drawPoint(box.getWidth() - VOID_LEN - GlobalConfig.ONE, i, setting);
            canvas.drawPoint(i, box.getHeight() - VOID_LEN - GlobalConfig.ONE, setting);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(box, 0, 0, null);
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
