package com.example.First_prj.ForAllCode.Gradients;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.R;

public class BubbleHorizontalGradientLine extends View {
    public BubbleHorizontalGradientLine(Context context, int height) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height));
        super.setBackgroundResource(R.drawable.ballgradient);
    }
}
