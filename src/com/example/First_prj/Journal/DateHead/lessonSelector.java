package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;

public class LessonSelector extends LinearLayout {

    private SerifTextView lessonName;
    private final String DEFAULT_TEXT = "Выбрать предмет";

    public LessonSelector(Context context) {
        super(context);
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        super.setLayoutParams(new ViewGroup.LayoutParams(
                LookingJournalConfig.getLessonSelectorWidth(context),
                LookingJournalConfig.getLessonSelectorHeight()));
        super.setGravity(Gravity.CENTER);
        lessonName = new SerifTextView(context, DEFAULT_TEXT, GlobalConfig.HEADER_TEXT_SIZE);
        super.addView(lessonName);
    }

    public void setLessonName(String name) {
        this.lessonName.setText(name);
    }

    public void setDefaultText() {
        lessonName.setText(DEFAULT_TEXT);
    }

}
