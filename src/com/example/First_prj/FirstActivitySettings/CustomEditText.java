package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;

public class CustomEditText extends EditText {

    private float metric = getContext().getResources().getDisplayMetrics().density;

    public CustomEditText(Context context, byte maxSymbolCount) {
        super(context);
        super.setInputType(InputType.TYPE_CLASS_PHONE);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setTextColor(Color.WHITE);
        super.setWidth((int) (50 * metric));
        super.setHeight((int) (40 * metric));
        super.setTextSize(14);
        super.setGravity(Gravity.CENTER);
        super.setText("");
        super.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSymbolCount)});
        super.setTypeface(Typeface.SERIF);
    }


    @Override
    public void setWidth(int pixels) {
        super.setWidth((int) (pixels * metric));
    }
}
