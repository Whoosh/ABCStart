package com.example.First_prj.MenuAndSwitchers;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.LeftToRightHorizontalBoldGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;
import com.example.First_prj.JavaServer.Server;
import com.example.First_prj.Journal.MainJournalActivityStarter;

public class Menu extends LinearLayout implements View.OnTouchListener {

    private static final byte JOURNAL_ACTIVITY_ID = 2;

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
            itemsLayout[i] = new ItemMenu(context, Data.getFunctionName(sectionNumbersOfFunction[i]));
            scrollableLayout.addView(itemsLayout[i]);
            itemsLayout[i].setOnTouchListener(this);
        }

        scrollView.addView(scrollableLayout);
        setSettingsHead();
        super.addView(scrollView);
    }

    private void selectNewActivity(int pointToIndex) {
        for (int i = 0; i < Data.getLength(); i++)
            if (itemsLayout[pointToIndex].getStringText().equals(Data.getFunctionName(i)))
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
            case Server.TEACHER_CODE:
                return Data.getTeacherSet();
            case Server.STUDENT_CODE:
                return Data.getStudentSet();
            case Server.PARENT_CODE:
                return Data.getParentSet();
            case Server.STUDENT_TEACHER_CODE:
                return Data.getTeacherStudentSet();
            default: {
                return new int[]{1, 1}; // TODO обработку
            }
        }
    }

    //@TODO В каждом кейсе вызов своего активити.
    public void startActivity(int indexOfValue) {
        switch (indexOfValue) {
            case JOURNAL_ACTIVITY_ID: {
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


}
