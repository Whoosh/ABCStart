package ru.journal.fspoPrj.public_code.custom_desing_elements.lines;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class TransparentHorizontalLine extends View {

    public TransparentHorizontalLine(Context context, int height) {
        super(context);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, height));
    }
}

