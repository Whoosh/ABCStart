package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class SerifTextView extends TextView {
    private String text;
    private int textSize;

    public SerifTextView(Context context, String text, int textSize) {
        super(context);
        this.text = text;
        this.textSize = textSize;
        super.setText(text);
        super.setTextSize(textSize);
        super.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
        super.setTypeface(Typeface.SERIF);
        super.setGravity(Gravity.CENTER);
    }

    public int getCurrentWight() {
        return ((text.length() - Constants.ONE) * (textSize - textSize / 5));
    }

    public String getStringText() {
        return text;
    }
}
