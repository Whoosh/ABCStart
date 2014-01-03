package ru.journal.fspoPrj.public_code.custom_desing_elements.lines;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class VerticalLine extends View {
    public VerticalLine(Context context, int color, int wight) {
        super(context);
        initCode(color, wight);
    }

    public VerticalLine(Context context, int color) {
        super(context);
        initCode(color, GlobalConfig.ONE);
    }

    private void initCode(int color, int wight) {
        super.setLayoutParams(new ViewGroup.LayoutParams(wight, FILL_PARENT));
        super.setBackgroundDrawable(new ColorDrawable(color));
    }
}
