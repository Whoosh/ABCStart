package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.R;

public class BoldGradientLine extends View {
    public BoldGradientLine(Context context, int wight) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, wight));
        super.setBackgroundResource(R.drawable.boldgradien);
    }
}