package ru.journal.fspoPrj.public_code.custom_desing_elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

import static ru.journal.fspoPrj.public_code.configs.GlobalConfig.getSerifTextColor;

public class SerifTextView extends TextView {

    private String text;

    public SerifTextView(Context context, String text, float textSize) {
        super(context);
        initCode(text, Gravity.CENTER, textSize);
    }

    public SerifTextView(Context context, String text) {
        super(context);
        initCode(text, Gravity.CENTER, GlobalConfig.getDefaultTextSize());
    }

    public SerifTextView(Context context, int gravity, String text, float textSize) {
        super(context);
        initCode(text, gravity, textSize);
    }

    public SerifTextView(Context context, int gravity, String text) {
        super(context);
        initCode(text, gravity, GlobalConfig.getDefaultTextSize());
    }


    private void initCode(String text, int gravity, float textSize) {
        this.text = text;
        super.setTextColor(getSerifTextColor());
        super.setText(text);
        // FIXME
        //     super.setTextSize(textSize);
        // super.setTypeface(Typeface.SERIF);
        super.setGravity(gravity);
    }

    public int getCurrentWight() {
        return super.getWidth();
    }

    public String getStringText() {
        return text;
    }

    public void setText(String text) {
        super.setText(text);
    }

    public void setColor(int color) {
        super.setTextColor(color);
    }
}
