package ru.journal.fspoPrj.journal.looking_journal.elements.head_selector;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class LessonSelector extends LinearLayout implements View.OnClickListener {

    private SerifTextView lessonName;
    private static final String DEFAULT_TEXT = "Выбрать предмет";

    public LessonSelector(Context context) {
        super(context);
       /*/
        super.setBackgroundColor(Config.getBackgroundColor());
        super.setLayoutParams(new ViewGroup.LayoutParams(
                Config.getLessonSelectorWidth(context),
                Config.getLessonSelectorHeight()));
       /*/
        super.setGravity(Gravity.CENTER);
        lessonName = new SerifTextView(context, DEFAULT_TEXT, GlobalConfig.getHeaderTextSize());
        super.addView(lessonName);
    }

    public void setLessonName(String name) {
        this.lessonName.setText(name);
    }

    public void setDefaultText() {
        lessonName.setText(DEFAULT_TEXT);
    }

    @Override
    public void onClick(View view) {

    }
}
