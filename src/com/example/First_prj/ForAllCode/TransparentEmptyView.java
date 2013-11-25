package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class TransparentEmptyView extends View {

    public TransparentEmptyView(Context context, int height) {
        super(context);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, (int) (height *
                context.getResources().getDisplayMetrics().density)));
    }
}
