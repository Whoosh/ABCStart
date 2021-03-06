package ru.journal.fspoPrj.public_code.custom_desing_elements.lines;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class HorizontalLine extends View {

    public static final int DEFAULT_HEIGHT = 1;

    public HorizontalLine(Context context, int color, int height) {
        super(context);
        initCode(color, height);
    }

    public HorizontalLine(Context context, int color) {
        super(context);
        initCode(color, DEFAULT_HEIGHT);
    }

    private void initCode(int color, int height) {
        super.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, height));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
