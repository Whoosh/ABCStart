package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

public class HorizontalLine extends View {

    public HorizontalLine(Context context, int color, int height) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                height));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
