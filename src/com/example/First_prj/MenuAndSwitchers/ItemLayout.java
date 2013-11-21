package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.BlueLine;

public class ItemLayout extends LinearLayout {
    private TextFieldLayout textFieldLayout;

    public ItemLayout(Context context, TextFieldLayout textFieldLayout) {
        super(context);
        this.textFieldLayout = textFieldLayout;
        super.setOrientation(VERTICAL);
        super.addView(new BlueLine(context, 1));
        super.addView(textFieldLayout);
        super.addView(new BlueLine(context, 1));
    }

    public String getStringText() {
        return textFieldLayout.getStringText();
    }
}
