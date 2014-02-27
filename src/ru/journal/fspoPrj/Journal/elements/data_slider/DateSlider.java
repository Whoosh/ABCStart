package ru.journal.fspoPrj.journal.elements.data_slider;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

public class DateSlider extends LinearLayout {

    private LinearLayout datesSlider;
    private Context context;
    private OnClickListener listener;

    public DateSlider(Context context) {
        super(context);
        this.context = context;
        datesSlider = new LinearLayout(context);
    }

    public void setData(LightExercisesInfo[] datesInfo) {
        super.removeAllViews();
        datesSlider.removeAllViews();
        datesSlider.addView(new VerticalLine(context, Color.BLACK));
        DateElement element;

        for (int i = 0; i < datesInfo.length - 1; i++) {
            element = new DateElement(context, datesInfo[i], i);
            setListener(element);
            datesSlider.addView(element);
            datesSlider.addView(new VerticalLine(context, Color.BLACK));
        }

        element = new DateElement(context, datesInfo[datesInfo.length - 1], datesInfo.length - 1);
        datesSlider.addView(element);
        datesSlider.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        addView(datesSlider);
    }

    private void setListener(DateElement element) {
        if (listener != null) {
            element.setOnClickListener(listener);
        }
    }

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void restoreState(LightExercisesInfo[] exercisesInfo) {
        setData(exercisesInfo);
    }
}
