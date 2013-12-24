package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;

public class LessonSelector extends LinearLayout {
//
    private SerifTextView nameOfElement;
    private final String DEFAULT_TEXT = "Выбрать предмет";

    public LessonSelector(Context context) {
        super(context);
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        super.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2,
                (int) (50 * context.getResources().getDisplayMetrics().density)));
        super.setGravity(Gravity.CENTER);
        nameOfElement = new SerifTextView(context, DEFAULT_TEXT, 20);
        nameOfElement.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2
                , ViewGroup.LayoutParams.FILL_PARENT));
        super.addView(nameOfElement);
    }

    public void setSetterName(String name) {
        this.nameOfElement.setText(name);
    }

    public void setDefaultText(){
       nameOfElement.setText(DEFAULT_TEXT);
    }

}
