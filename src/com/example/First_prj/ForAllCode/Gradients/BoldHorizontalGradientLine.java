package com.example.First_prj.ForAllCode.Gradients;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.R;

public class BoldHorizontalGradientLine extends View {
    public BoldHorizontalGradientLine(Context context, int wight) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, wight));
        super.setBackgroundResource(R.drawable.boldgradien);
    }
}
