package com.example.First_prj.ForAllCode.DesigneElements;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.First_prj.ForAllCode.GlobalConfig;
import com.example.First_prj.ForAllCode.GlobalConstants;

public class SerifTextView extends TextView {
    private String text;
    private int textSize;

    public SerifTextView(Context context, String text, int textSize) {
        super(context);
        initCode(text,textSize);
    }

    public SerifTextView(Context context, String text) {
        super(context);
        initCode(text,GlobalConstants.DEFAULT_TEXT_SIZE);
    }

    private void initCode(String text, int textSize){
        this.text = text;
        this.textSize = textSize;
        super.setTextColor(GlobalConfig.getSerifTextColor());
        super.setText(text);
        super.setTextSize(textSize);
        super.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
        super.setTypeface(Typeface.SERIF);
        super.setGravity(Gravity.CENTER);
    }


    public int getCurrentWight() {
        final byte downCo = 5;
        return ((text.length() - GlobalConstants.ONE) * (textSize - textSize / downCo));
    }

    public String getStringText() {
        return text;
    }
}
