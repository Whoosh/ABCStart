package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.ForAllCode.GlobalConstants;

public class VerticalLine extends View {
    public VerticalLine(Context context, int color, int wight) {
        super(context);
        initCode(color, wight);
    }

    public VerticalLine(Context context, int color) {
        super(context);
        initCode(color, GlobalConstants.ONE);
    }

    private void initCode(int color, int wight) {
        super.setLayoutParams(new ViewGroup.LayoutParams(wight,ViewGroup.LayoutParams.FILL_PARENT));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
