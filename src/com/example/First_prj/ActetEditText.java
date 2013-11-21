package com.example.First_prj;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;

public class ActetEditText extends EditText {
    //@TODO Если не запилить сюда логику, удалить.
    private final float metric = getContext().getResources().getDisplayMetrics().density;
    private final byte maxActetLen = 3;
    public ActetEditText(Context context) {
        super(context);
        super.setInputType(InputType.TYPE_CLASS_PHONE);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setTextColor(Color.WHITE);
        super.setWidth((int) (50 * metric));
        super.setHeight((int) (40 * metric));
        super.setTextSize(14);
        super.setGravity(Gravity.CENTER);
        super.setText("");
        super.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxActetLen)});
    }

}
