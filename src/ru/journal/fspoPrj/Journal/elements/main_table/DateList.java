package ru.journal.fspoPrj.journal.elements.main_table;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class DateList extends HorizontalScrollView {

    public DateList(Context context, ArrayList<String> dates) {
        super(context);
        super.setBackgroundColor(Config.getBackgroundColor());
        LinearLayout elements = new LinearLayout(context);
        elements.setBackgroundColor(Config.getDateListBackgroundColor());
        for (int i = 0; i < dates.size(); i++) {
            elements.addView(new DateElement(context, dates.get(i), i));
            elements.addView(new VerticalLine(context, Config.getSeparateLineColor()));
        }
        super.addView(elements);
    }


    private class DateElement extends FrameLayout implements View.OnClickListener {

        private final int indexOfDatePosition;

        public DateElement(Context context, String date, int index) {
            super(context);
            indexOfDatePosition = index;
            super.setLayoutParams(new ViewGroup.LayoutParams(
                    Config.getDateCellSize(),
                    Config.getDateCellSize()));
            super.setOnClickListener(this);
            super.addView(new SerifTextView(context, date, GlobalConfig.DEFAULT_TEXT_SIZE));
        }

        @Override
        public void onClick(View view) {
            //TODO
            System.out.println(indexOfDatePosition);
        }
    }
}
