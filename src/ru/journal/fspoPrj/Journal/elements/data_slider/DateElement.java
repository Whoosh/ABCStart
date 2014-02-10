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
        setTextSize(Config.getDateTextSize());
        setLayoutParams(new ViewGroup.LayoutParams(Config.getDateElementWidth(),Config.getDateElementHeight()));
    }
}
