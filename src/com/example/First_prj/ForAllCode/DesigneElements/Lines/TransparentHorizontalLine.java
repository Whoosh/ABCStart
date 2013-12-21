package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class TransparentHorizontalLine extends View {

    public TransparentHorizontalLine(Context context, int height) {
        super(context);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height));
    }
}
