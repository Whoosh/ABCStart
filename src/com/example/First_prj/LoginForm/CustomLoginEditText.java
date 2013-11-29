package com.example.First_prj.LoginForm;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.First_prj.ForAllCode.Constants;

public class CustomLoginEditText extends EditText {

    public CustomLoginEditText(Context context) {
        super(context);
        float metric = context.getResources().getDisplayMetrics().density;
        super.setLayoutParams(new ViewGroup.LayoutParams((int) (200 * metric), (int) (40 * metric)));
        super.setBackgroundColor(Color.argb(200, 25, 25, 25));
        super.setTextColor(Color.rgb(0, 100, 0));
        super.setTextSize(Constants.DEFAULT_TEXT_SIZE);
        super.setGravity(Gravity.BOTTOM);
        super.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        super.setHintTextColor(Color.rgb(0, 100, 0));
    }
}
