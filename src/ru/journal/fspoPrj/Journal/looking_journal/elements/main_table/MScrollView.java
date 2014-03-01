package ru.journal.fspoPrj.journal.looking_journal.elements.main_table;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MScrollView extends ScrollView {

    public MScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
