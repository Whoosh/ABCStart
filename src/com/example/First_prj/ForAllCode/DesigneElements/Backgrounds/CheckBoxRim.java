package com.example.First_prj.ForAllCode.DesigneElements.Backgrounds;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.ForAllCode.Configs.MainSettingsConfig;

public class CheckBoxRim extends Drawable {

    private Bitmap box;

    public CheckBoxRim() {
        box = Bitmap.createBitmap(MainSettingsConfig.getCheckBoxSize(),MainSettingsConfig.getCheckBoxSize(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(box);
        setBoxGround(canvas);
    }

    private void setBoxGround(Canvas canvas) {
        Paint setting = new Paint();
        setting.setColor(MainSettingsConfig.getCheckBoxColor());
        canvas.drawColor(MainSettingsConfig.getFormBackgroundColor());

        final byte VOID_LEN = 10;
        for (int i = VOID_LEN - GlobalConfig.ONE; i < MainSettingsConfig.getCheckBoxSize() - VOID_LEN; i++) {
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
