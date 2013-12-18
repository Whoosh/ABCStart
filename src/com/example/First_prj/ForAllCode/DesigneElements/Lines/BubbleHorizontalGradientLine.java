package com.example.First_prj.ForAllCode.DesigneElements.Lines;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.First_prj.ForAllCode.GlobalConstants;
import com.example.First_prj.R;

public class BubbleHorizontalGradientLine extends View {
    public BubbleHorizontalGradientLine(Context context, int height) {
        super(context);
        initCode(height);
    }

    public BubbleHorizontalGradientLine(Context context) {
        super(context);
        initCode(GlobalConstants.ONE);
    }

    private void initCode(int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height));
        super.setBackgroundResource(R.drawable.bubblegradien);
    }
}
