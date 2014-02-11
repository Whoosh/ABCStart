package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.Month;

public class DateElement extends Button {

    public DateElement(Context context, Month month) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(Config.getDateDialogElementWidth(), Config.getDateDialogElementHeight()));
        setText(month.getMonth());
        setTextSize(Config.getDateDialogElementTextSize());
    }
}
