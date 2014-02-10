package ru.journal.fspoPrj.journal.elements.group_selector;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class GroupSelector extends LinearLayout {

    ArrayList<String[]> courses;

    public GroupSelector(Context context, ArrayList<String[]> courses) {
        super(context);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        this.courses = courses;

        addView(new HorizontalLine(context, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
        addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getOnGroupDialogTransparentSeparateLineHeight()));

        for (String[] course : courses) {
            LinearLayout row = new LinearLayout(context);
            for (String group : course) {
                row.addView(new GroupElement(context, group));
                row.addView(new VerticalLine(context, Color.TRANSPARENT, Config.getOnGroupDialogElementTransparentSeparateLineSize()));
            }
            addView(row);
            addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getOnGroupDialogElementTransparentSeparateLineSize()));
        }

    }
}
