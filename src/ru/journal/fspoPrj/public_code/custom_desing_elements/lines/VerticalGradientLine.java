package ru.journal.fspoPrj.public_code.custom_desing_elements.lines;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class VerticalGradientLine extends View {

    public static final int DEFAULT_VALUE = 1;

    public VerticalGradientLine(Context context, int resID, int width) {
        super(context);
        initCode(width, resID);
    }

    public VerticalGradientLine(Context context, int resID) {
        super(context);
        initCode(DEFAULT_VALUE, resID);
    }

    private void initCode(int width, int resID) {
        super.setLayoutParams(new ViewGroup.LayoutParams(width, FILL_PARENT));
        super.setBackgroundResource(resID);
    }
}

