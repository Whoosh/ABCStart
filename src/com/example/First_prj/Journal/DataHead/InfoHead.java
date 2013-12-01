package com.example.First_prj.Journal.DataHead;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.VerticalLine;

public class InfoHead extends LinearLayout {

    public InfoHead(Context context) {
        super(context);
        super.setOrientation(HORIZONTAL);
        SerifTextView data = new SerifTextView(context, "Дата", Constants.DEFAULT_TEXT_SIZE);
        SerifTextView group = new SerifTextView(context, "Группа", Constants.DEFAULT_TEXT_SIZE);
        data.setGravity(Gravity.CENTER);
        group.setGravity(Gravity.CENTER);
        data.setLayoutParams(new ViewGroup.LayoutParams((((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth() / 2),ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setLayoutParams(new ViewGroup.LayoutParams((((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth() / 2), ViewGroup.LayoutParams.WRAP_CONTENT));
        super.addView(data);
        super.addView(group);
    }
}
