package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;

public class ItemMenu extends LinearLayout {

    private SerifTextView textView;
    private LinearLayout itemTextIcon;

    public ItemMenu(Context context, String itemText) {
        super(context);
        textView = new SerifTextView(context, itemText, 20);
        itemTextIcon = new LinearLayout(context);
        itemTextIcon.setOrientation(HORIZONTAL);
        itemTextIcon.addView(new IconSetter(context, android.R.drawable.ic_media_play));
        itemTextIcon.addView(textView);

        super.setOrientation(VERTICAL);
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(itemTextIcon);
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(new TransparentHorizontalLine(context, 20));
    }

    public String getStringText() {
        return textView.getStringText();
    }

    public void setBlinkedColor() {
        itemTextIcon.setBackgroundColor(Color.parseColor("#6BFF66")); // ярко зелёный
    }

    public void setBlinkedColorBack() {
        itemTextIcon.setBackgroundColor(Color.TRANSPARENT);
    }
}
