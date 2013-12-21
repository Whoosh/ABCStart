package com.example.First_prj.ForAllCode.DesigneElements.Backgrounds;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import com.example.First_prj.ForAllCode.GlobalConfig;
import com.example.First_prj.ForAllCode.GlobalConstants;

public class CheckBoxRim extends Drawable {

    private Bitmap box;
    private final byte VOID_LEN = 10;

    public CheckBoxRim() {
        box = Bitmap.createBitmap(
                GlobalConfig.MainSettingsConfig.getCheckBoxSize(),
                GlobalConfig.MainSettingsConfig.getCheckBoxSize(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(box);
        setBoxGround(canvas);
    }

    private void setBoxGround(Canvas canvas) {
        Paint setting = new Paint();
        setting.setColor(GlobalConfig.MainSettingsConfig.getCheckBoxColor());
        canvas.drawColor(GlobalConfig.MainSettingsConfig.getFormBackgroundColor());

        for (int i = VOID_LEN - GlobalConstants.ONE; i < GlobalConfig.MainSettingsConfig.getCheckBoxSize() - VOID_LEN; i++) {
            canvas.drawPoint(i, VOID_LEN - GlobalConstants.ONE, setting);
            canvas.drawPoint(VOID_LEN - GlobalConstants.ONE, i, setting);
            canvas.drawPoint(box.getWidth() - VOID_LEN - GlobalConstants.ONE, i, setting);
            canvas.drawPoint(i, box.getHeight() - VOID_LEN - GlobalConstants.ONE, setting);
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
