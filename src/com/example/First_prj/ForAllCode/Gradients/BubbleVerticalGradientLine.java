package com.example.First_prj.ForAllCode.Gradients;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.R;

public class BubbleVerticalGradientLine extends View {

    public BubbleVerticalGradientLine(Context context,int weight) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(weight, ViewGroup.LayoutParams.FILL_PARENT));
        super.setBackgroundResource(R.drawable.ballgradient);
    }
}
