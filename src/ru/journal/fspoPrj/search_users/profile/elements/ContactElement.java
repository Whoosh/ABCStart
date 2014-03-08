package ru.journal.fspoPrj.search_users.profile.elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactElement extends LinearLayout {

    private static final int DEFAULT_COLOR = Color.parseColor("#C6C8C7");
    private static final int PRESSED_COLOR = Color.parseColor("#98F1E9");

    private TextView textElement;

    public ContactElement(Context context, String text, OnClickListener clickListener) {
        super(context);
        super.setBackgroundColor(DEFAULT_COLOR);
        super.setOnClickListener(clickListener);
        textElement = new TextView(context);
        textElement.setText(text);
        // textElement.setTextSize(Config.getExtendUserInfoNamesTextSize());
        textElement.setTypeface(Typeface.SANS_SERIF);
        super.addView(textElement);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                setBackgroundColor(PRESSED_COLOR);
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
            default: {
                setBackgroundColor(DEFAULT_COLOR);
            }
        }
        return super.onTouchEvent(event);
    }

    public String getData() {
        return textElement.getText().toString();
    }
}
