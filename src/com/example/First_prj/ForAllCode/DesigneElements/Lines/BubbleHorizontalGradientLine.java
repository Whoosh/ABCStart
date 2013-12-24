package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.R;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
//
public class BubbleHorizontalGradientLine extends View {
    public BubbleHorizontalGradientLine(Context context, int height) {
        super(context);
        initCode(height);
    }

    public BubbleHorizontalGradientLine(Context context) {
        super(context);
        initCode(GlobalConfig.ONE);
    }

    private void initCode(int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, height));
        super.setBackgroundResource(R.drawable.bubblegradient);
    }
}

