package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.ForAllCode.GlobalConstants;

public class HorizontalLine extends View {

    public HorizontalLine(Context context, int color, int height) {
        super(context);
        initCode(color, height);
    }

    public HorizontalLine(Context context, int color) {
        super(context);
        initCode(color, GlobalConstants.ONE);
    }

    private void initCode(int color, int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                height));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
