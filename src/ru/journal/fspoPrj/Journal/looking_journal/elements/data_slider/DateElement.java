package ru.journal.fspoPrj.journal.looking_journal.elements.data_slider;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;

public class DateElement extends TextView {

    private LightExercisesInfo date;
    private int tableIndex;
    private int cellColor;

    public DateElement(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(Config.getSliderDateElementWidth(), Config.getSliderDateElementHeight()));
        setGravity(Gravity.CENTER);
    }

    public DateElement(Context context, LightExercisesInfo date, int tableIndex) {
        super(context);
        this.date = date;
        this.tableIndex = tableIndex;
        this.cellColor = LightExercisesInfo.TypeState.values()[date.getType()].getColor();
        setText(date.getDMDate());
        setBackgroundColor(cellColor);
        setGravity(Gravity.CENTER);
        setTextSize(Config.getDateSliderTextSize());
        setLayoutParams(new ViewGroup.LayoutParams(Config.getSliderDateElementWidth(), Config.getSliderDateElementHeight()));
    }

    public boolean isEmpty() {
        return (date == null);
    }

    public String getDate() {
        return date.getDMDate();
    }

    public int getCellColor() {
        return cellColor;
    }

    public int getTableIndex() {
        return tableIndex;
    }
}
