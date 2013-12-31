package ru.journal.fspoPrj.public_code.custom_desing_elements;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

import static ru.journal.fspoPrj.public_code.configs.GlobalConfig.getSerifTextColor;
//

public class SerifTextView extends TextView {

    private String text;
    private int textSize;

    public SerifTextView(Context context, String text, int textSize) {
        super(context);
        initCode(text, Gravity.CENTER, textSize);
    }

    public SerifTextView(Context context, String text) {
        super(context);
        initCode(text, Gravity.CENTER, GlobalConfig.DEFAULT_TEXT_SIZE);
    }

    public SerifTextView(Context context, int gravity, String text, int textSize) {
        super(context);
        initCode(text, gravity, textSize);
    }

    public SerifTextView(Context context, int gravity, String text) {
        super(context);
        initCode(text, gravity, GlobalConfig.DEFAULT_TEXT_SIZE);
    }

    private void initCode(String text, int gravity, int textSize) {
        this.text = text;
        this.textSize = textSize;
        super.setTextColor(getSerifTextColor());
        super.setText(text);
        super.setTextSize(textSize);
        super.setTypeface(Typeface.SERIF);
        super.setGravity(gravity);
    }


    public int getCurrentWight() {
        //final byte downCo = 5;
        //return ((text.length() - GlobalConfig.ONE) * (textSize - textSize / downCo));
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
