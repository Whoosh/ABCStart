package ru.journal.fspoPrj.journal.elements.data_slider;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.journal.fspoPrj.journal.config.Config;

public class DateElement extends TextView {

    public DateElement(Context context, String date) {
        super(context);
        setText(date);
        setGravity(Gravity.CENTER);
        setTextSize(Config.getDateSliderTextSize());
        setLayoutParams(new ViewGroup.LayoutParams(Config.getSliderDateElementWidth(),Config.getSliderDateElementHeight()));
    }
}
