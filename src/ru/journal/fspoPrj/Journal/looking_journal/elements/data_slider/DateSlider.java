package ru.journal.fspoPrj.journal.looking_journal.elements.data_slider;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
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

    public void setData(ArrayList<LightExercisesInfo> datesInfo) {
        super.removeAllViews();
        datesSlider.removeAllViews();
        datesSlider.addView(new VerticalLine(context, Color.BLACK));
        DateElement element;

        for (int i = 0; i < datesInfo.size() - 1; i++) {
            element = new DateElement(context, datesInfo.get(i), i);
            datesSlider.addView(element);
            datesSlider.addView(new VerticalLine(context, Color.BLACK));
        }

        element = new DateElement(context, datesInfo.get(datesInfo.size()- 1), datesInfo.size()- 1);
        datesSlider.addView(element);
        datesSlider.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        addView(datesSlider);
    }

    public void restoreState(ArrayList<LightExercisesInfo> exercisesInfo) {
        setData(exercisesInfo);
    }
}
