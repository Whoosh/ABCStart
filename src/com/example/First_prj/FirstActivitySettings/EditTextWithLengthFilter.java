package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.First_prj.Constants;

public class EditTextWithLengthFilter extends EditText {

    private float metric;

    public EditTextWithLengthFilter(Context context, byte maxSymbolCount) {
        super(context);
        metric = context.getResources().getDisplayMetrics().density;
        super.setInputType(InputType.TYPE_CLASS_PHONE);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setTextColor(Color.WHITE);
        super.setWidth((int) (50 * metric));
        super.setHeight((int) (40 * metric));
        super.setTextSize(Constants.DEFAULT_TEXT_SIZE);
        super.setGravity(Gravity.BOTTOM);
        super.setText("");
        super.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSymbolCount)});
        super.setTypeface(Typeface.SERIF);
    }

    @Override
    public void setWidth(int pixels) {
        super.setWidth((int) (pixels * metric));
    }
}
