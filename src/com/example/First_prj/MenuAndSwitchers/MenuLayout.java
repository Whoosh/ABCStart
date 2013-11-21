package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.*;

public class MenuLayout extends LinearLayout implements View.OnTouchListener {

    private ItemLayout itemsLayout[];
    private Context context;
    private int itemsLength;
    private MenuSwitcherActivity menuSwitcherActivity;

    public MenuLayout(Context context, String[] sections) {
        super(context);
        super.setOrientation(VERTICAL);
        this.context = context;
        final byte height = 20;
        itemsLength = sections.length;
        menuSwitcherActivity = new MenuSwitcherActivity(context);
        LinearLayout scrollableLayout = new LinearLayout(context);
        ScrollView scrollView = new ScrollView(context);
        itemsLayout = new ItemLayout[itemsLength];

        scrollableLayout.setOrientation(VERTICAL);

        for (int i = 0; i < itemsLength; i++) {
            itemsLayout[i] = new ItemLayout(context, new TextFieldLayout(
                    context, new SerifTextView(context, sections[i], height)));

            scrollableLayout.addView(itemsLayout[i]);
            scrollableLayout.addView(new TransparentEmptyView(context, height));
            itemsLayout[i].setOnTouchListener(this);
        }

        scrollView.addView(scrollableLayout);
        addHead();
        super.addView(scrollView);
    }

    private void addHead() {
        LinearLayout headLayout = new LinearLayout(context);
        headLayout.setOrientation(LinearLayout.HORIZONTAL);
        headLayout.addView(new Icon(context, android.R.drawable.ic_menu_agenda));
        headLayout.addView(new SerifTextView(context, "\tМеню", 22));

        super.addView(headLayout);
        super.addView(new BoldLine(context, 3));
        super.addView(new TransparentEmptyView(context, 15));
    }

    private void selectNewActivity(int pointToIndex) {
        for (int i = 0; i < menuSwitcherActivity.getActivityCounts(); i++)
            if (itemsLayout[pointToIndex].getStringText().equals(menuSwitcherActivity.getActivityKey(i)))
                menuSwitcherActivity.startActivity(i);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int i = 0; i < itemsLength; i++)
            if (view.equals(itemsLayout[i]) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                itemsLayout[i].setBackgroundColor(Color.parseColor("#6BFF66"));
                selectNewActivity(i);
                return true;
            } else if (view.equals(itemsLayout[i])) {
                itemsLayout[i].setBackgroundColor(Color.TRANSPARENT);
                return true;
            }
        return true;
    }

    public void setSelectedItemColorBack() {
        for (int i = 0; i < itemsLength; i++)
            itemsLayout[i].setBackgroundColor(Color.TRANSPARENT);
    }
}
