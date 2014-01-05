package ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.settings_form.config.Config;

public class CheckBoxRim extends Drawable {

    private Bitmap box;
    private static final byte VOID_LEN = 10;

    public CheckBoxRim() {
        box = Bitmap.createBitmap(
                Config.getCheckBoxSize(),
                Config.getCheckBoxSize(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(box);
        setBoxGround(canvas);
    }

    private void setBoxGround(Canvas canvas) {
        Paint setting = new Paint();
        setting.setColor(Config.getCheckBoxColor());
        canvas.drawColor(Config.getFormBackgroundColor());


        for (int i = VOID_LEN - GlobalConfig.ONE; i < Config.getCheckBoxSize() - VOID_LEN; i++) {
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
