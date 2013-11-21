package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.First_prj.ForAllCode.Icon;
import com.example.First_prj.ForAllCode.SerifTextView;

public class TextFieldLayout extends LinearLayout {

    private SerifTextView textView;

    public TextFieldLayout(Context context, SerifTextView textView) {
        super(context);
        this.textView = textView;
        super.setOrientation(HORIZONTAL);
        super.addView(new Icon(context, android.R.drawable.ic_media_play));
        super.addView(textView);
    }

    public String getStringText() {
        return textView.getStringText();
    }
}
