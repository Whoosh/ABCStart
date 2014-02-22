package ru.journal.fspoPrj.journal.elements.data_slider;

import android.content.Context;
import android.graphics.Color;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class DateSlider extends LinearLayout {

    private LinearLayout datesSlider;
    private Context context;

    public DateSlider(Context context) {
        super(context);
        this.context = context;
        datesSlider = new LinearLayout(context);
    }

    public void setData(LightExercisesInfo[] datesInfo) {
        super.removeAllViews();
        datesSlider.removeAllViews();
        datesSlider.addView(new VerticalLine(context, Color.BLACK));
        for (int i = 0; i < datesInfo.length - 1; i++) {
            datesSlider.addView(new DateElement(context, datesInfo[i]));
            datesSlider.addView(new VerticalLine(context, Color.BLACK));
        }
        datesSlider.addView(new DateElement(context, datesInfo[datesInfo.length - 1]));
        datesSlider.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        addView(datesSlider);
    }


    public void restoreState(LightExercisesInfo[] exercisesInfo) {
        setData(exercisesInfo);
    }

    public void dropDate() {
        removeAllViews();
    }
}
