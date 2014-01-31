package ru.journal.fspoPrj.public_code.custom_desing_elements.lines;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.R;


import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class HorizontalGradientLine extends View {

    public static final int DEFAULT_VALUE = 1;

    public HorizontalGradientLine(Context context, int resID, int height) {
        super(context);
        initCode(height, resID);
    }

    public HorizontalGradientLine(Context context, int resID) {
        super(context);
        initCode(DEFAULT_VALUE, resID);
    }

    private void initCode(int height, int resID) {
        super.setLayoutParams(new ViewGroup.LayoutParams(FILL_PARENT, height));
        super.setBackgroundResource(resID);
    }
}

