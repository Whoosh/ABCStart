package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class SerifTextView extends TextView {
    private String text;

    public SerifTextView(Context context, String text, int textSize) {
        super(context);
        this.text = text;
        super.setText(text);
        super.setTextSize(textSize);
        super.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT));
        super.setTypeface(Typeface.SERIF);
        super.setGravity(Gravity.CENTER_VERTICAL);
    }

    public String getStringText() {
        return text;
    }
}
