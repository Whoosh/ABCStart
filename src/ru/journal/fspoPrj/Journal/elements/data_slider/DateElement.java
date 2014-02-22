package ru.journal.fspoPrj.journal.elements.data_slider;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;

public class DateElement extends TextView {

    public DateElement(Context context, LightExercisesInfo date) {
        super(context);
        setText(date.getDMDate());
        setBackgroundColor(LightExercisesInfo.TypeState.values()[date.getType()].getColor());
        setGravity(Gravity.CENTER);
        setTextSize(Config.getDateSliderTextSize());
        setLayoutParams(new ViewGroup.LayoutParams(Config.getSliderDateElementWidth(), Config.getSliderDateElementHeight()));
    }
}
