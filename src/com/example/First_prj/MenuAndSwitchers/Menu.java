package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.LeftToRightHorizontalBoldGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;
import com.example.First_prj.JavaServer.MightInfo;
import com.example.First_prj.Journal.MainJournalActivityStarter;

public class Menu extends LinearLayout implements View.OnTouchListener {

    private static final String MENU_TITLE = "\tМеню";
    private static final byte MENU_TITLE_TEXT_SIZE = 22;

    private ItemMenu itemsLayout[];
    private Context context;
    private final int[] sectionNumbersOfFunction;

    public Menu(Context context, int mightCode) {
        super(context);
        super.setOrientation(VERTICAL);
        this.context = context;

        LinearLayout scrollableLayout = new LinearLayout(context);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setVerticalScrollBarEnabled(false);

        sectionNumbersOfFunction = getSections(mightCode);

        itemsLayout = new ItemMenu[sectionNumbersOfFunction.length];

        scrollableLayout.setOrientation(VERTICAL);
        for (int i = 0; i < sectionNumbersOfFunction.length; i++) {
            itemsLayout[i] = new ItemMenu(context, FunctionsIDAndNames.getFunctionName(sectionNumbersOfFunction[i]));
            scrollableLayout.addView(itemsLayout[i]);
            itemsLayout[i].setOnTouchListener(this);
        }

        scrollView.addView(scrollableLayout);
        setSettingsHead();
        super.addView(scrollView);
    }

    private void selectNewActivity(int pointToIndex) {
        for (int i = 0; i < FunctionsIDAndNames.getLength(); i++)
            if (itemsLayout[pointToIndex].getStringText().equals(FunctionsIDAndNames.getFunctionName(i)))
                startActivity(i);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int i = 0; i < sectionNumbersOfFunction.length; i++)
            if (view.equals(itemsLayout[i]) && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                itemsLayout[i].setBlinkedColor();
                selectNewActivity(i);
                return true;
            } else if (view.equals(itemsLayout[i])) {
                itemsLayout[i].setBlinkedColorBack();
                return true;
            }
        return true;
    }

    public void setBack() {
        for (int i = 0; i < sectionNumbersOfFunction.length; i++)
            itemsLayout[i].setBlinkedColorBack();
    }

    public int[] getSections(int mightCode) {
        switch (mightCode) {
            case MightInfo.ADMIN_MIGHT_CODE:
                return FunctionsIDAndNames.getAdminSet();
            case MightInfo.PARENT_MIGHT_CODE:
                return FunctionsIDAndNames.getParentSet();
            case MightInfo.STUDENT_AKA_TEACHER_MIGHT_CODE:
                return FunctionsIDAndNames.getTeacherAKAStudentSet();
            case MightInfo.STUDENT_MIGHT_CODE:
                return FunctionsIDAndNames.getStudentSet();
            case MightInfo.TEACHER_MIGHT_CODE:
                return FunctionsIDAndNames.getTeacherSet();
            case MightInfo.ANONYMOUS_MIGHT_CODE:
            default: {
                return FunctionsIDAndNames.getInformerForAnonSet(); // TODO
            }
        }
    }

    //@TODO В каждом кейсе вызов своего активити.
    public void startActivity(int indexOfValue) {
        switch (indexOfValue) {
            case FunctionsIDAndNames.JOURNAL_INDEX_AKA_JOURNAL_ACTIVITY_ID: {
                context.startActivity(new Intent(context, MainJournalActivityStarter.class));
                break;
            }
        }
    }

    private void setSettingsHead() {
        LinearLayout headLayout = new LinearLayout(context);
        headLayout.setOrientation(LinearLayout.HORIZONTAL);
        headLayout.addView(new IconSetter(context, android.R.drawable.ic_menu_agenda));
        headLayout.addView(new SerifTextView(context, MENU_TITLE, MENU_TITLE_TEXT_SIZE));
        super.addView(headLayout);
        super.addView(new LeftToRightHorizontalBoldGradientLine(context, 3));
        super.addView(new TransparentHorizontalLine(context, 15));
    }

    private class ItemMenu extends LinearLayout {

        private SerifTextView textView;
        private LinearLayout itemTextIcon;

        public ItemMenu(Context context, String itemText) {
            super(context);
            textView = new SerifTextView(context, itemText, 20);
            itemTextIcon = new LinearLayout(context);
            itemTextIcon.setOrientation(HORIZONTAL);
            itemTextIcon.addView(new IconSetter(context, android.R.drawable.ic_media_play));
            itemTextIcon.addView(textView);

            super.setOrientation(VERTICAL);
            super.addView(new BubbleHorizontalGradientLine(context));
            super.addView(itemTextIcon);
            super.addView(new BubbleHorizontalGradientLine(context));
            super.addView(new TransparentHorizontalLine(context, 20));
        }

        public String getStringText() {
            return textView.getStringText();
        }

        public void setBlinkedColor() {
            itemTextIcon.setBackgroundColor(Color.parseColor("#6BFF66")); // ярко зелёный
        }

        public void setBlinkedColorBack() {
            itemTextIcon.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
