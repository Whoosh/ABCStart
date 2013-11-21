package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.SerifTextView;

public class TabViewElement extends LinearLayout{

    public TabViewElement(Context context,String label) {
        super(context);
        super.setGravity(Gravity.CENTER);
        super.setOrientation(HORIZONTAL);
        super.addView(new SerifTextView(context,label,12));
        super.setBackgroundColor(Color.DKGRAY);
    }
}
