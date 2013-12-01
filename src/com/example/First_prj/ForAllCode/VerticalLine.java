package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FFX20413 on 01.12.13.
 */
public class VerticalLine extends View {
    public VerticalLine(Context context, int color, int wight) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(wight,
                ViewGroup.LayoutParams.FILL_PARENT));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
