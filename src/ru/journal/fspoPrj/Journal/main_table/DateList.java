package ru.journal.fspoPrj.journal.main_table;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.configs.LookingJournalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

import java.util.ArrayList;

public class DateList extends HorizontalScrollView {

    public DateList(Context context, ArrayList<String> dates) {
        super(context);
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        LinearLayout elements = new LinearLayout(context);
        elements.setBackgroundColor(LookingJournalConfig.getDateListBackgroundColor());
        for (int i = 0; i < dates.size(); i++) {
            elements.addView(new DateElement(context, dates.get(i), i));
            elements.addView(new VerticalLine(context, LookingJournalConfig.getSeparateLineColor()));
        }
        super.addView(elements);
    }


    private class DateElement extends FrameLayout implements View.OnClickListener {

        private final int indexOfDatePosition;

        public DateElement(Context context, String date, int index) {
            super(context);
            indexOfDatePosition = index;
            super.setLayoutParams(new ViewGroup.LayoutParams(
                    LookingJournalConfig.getDateCellSize(),
                    LookingJournalConfig.getDateCellSize()));
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
