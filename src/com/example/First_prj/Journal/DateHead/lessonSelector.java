package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;

public class LessonSelector extends LinearLayout {

    private SerifTextView name;

    public LessonSelector(Context context) {
        super(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        super.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2,
                (int) (50 * context.getResources().getDisplayMetrics().density)));
        super.setGravity(Gravity.CENTER);
        name = new SerifTextView(context, "Список предметов", Constants.DEFAULT_TEXT_SIZE);
        name.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2
                , ViewGroup.LayoutParams.FILL_PARENT));
        name.setBackgroundColor(Color.DKGRAY);
        super.addView(name);
    }

    public void setSetterName(String name) {
        this.name.setText(name);
    }

}
